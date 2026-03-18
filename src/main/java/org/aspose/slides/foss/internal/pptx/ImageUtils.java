package org.aspose.slides.foss.internal.pptx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

/**
 * Image utility functions for parsing image headers and detecting content types.
 *
 * <p>Supports JPEG, PNG, GIF, BMP, TIFF, EMF, and WMF formats without external dependencies.
 */
public final class ImageUtils {

    private ImageUtils() {
        // utility class
    }

    // --- Magic byte signatures for image format detection ---

    private static final byte[] PNG_SIGNATURE = {
            (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
    private static final byte[] JPEG_SIGNATURE = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
    private static final byte[] GIF87_SIGNATURE = {0x47, 0x49, 0x46, 0x38, 0x37, 0x61};
    private static final byte[] GIF89_SIGNATURE = {0x47, 0x49, 0x46, 0x38, 0x39, 0x61};
    private static final byte[] BMP_SIGNATURE = {0x42, 0x4D};
    private static final byte[] TIFF_LE_SIGNATURE = {0x49, 0x49, 0x2A, 0x00};
    private static final byte[] TIFF_BE_SIGNATURE = {0x4D, 0x4D, 0x00, 0x2A};
    private static final byte[] EMF_SIGNATURE = {0x01, 0x00, 0x00, 0x00};
    private static final byte[] WMF_SIGNATURE = {
            (byte) 0xD7, (byte) 0xCD, (byte) 0xC6, (byte) 0x9A};

    /** Extension to MIME type mapping. */
    public static final Map<String, String> extensionContentTypes = Map.ofEntries(
            Map.entry("png", "image/png"),
            Map.entry("jpg", "image/jpeg"),
            Map.entry("jpeg", "image/jpeg"),
            Map.entry("gif", "image/gif"),
            Map.entry("bmp", "image/bmp"),
            Map.entry("tiff", "image/tiff"),
            Map.entry("tif", "image/tiff"),
            Map.entry("emf", "image/x-emf"),
            Map.entry("wmf", "image/x-wmf"),
            Map.entry("svg", "image/svg+xml")
    );

    /** MIME type to default extension mapping. */
    public static final Map<String, String> contentTypeExtensions = Map.ofEntries(
            Map.entry("image/png", "png"),
            Map.entry("image/jpeg", "jpeg"),
            Map.entry("image/gif", "gif"),
            Map.entry("image/bmp", "bmp"),
            Map.entry("image/tiff", "tiff"),
            Map.entry("image/x-emf", "emf"),
            Map.entry("image/x-wmf", "wmf"),
            Map.entry("image/svg+xml", "svg")
    );

    /**
     * Detects the MIME content type of an image from its binary data.
     *
     * @param data raw image bytes (at least first 12 bytes needed)
     * @return MIME type string (e.g., {@code "image/jpeg"}).
     *         Returns {@code "application/octet-stream"} if unknown.
     */
    public static String guessContentType(byte[] data) {
        if (data == null || data.length < 4) {
            return "application/octet-stream";
        }

        if (startsWith(data, PNG_SIGNATURE)) {
            return "image/png";
        }
        if (startsWith(data, JPEG_SIGNATURE)) {
            return "image/jpeg";
        }
        if (startsWith(data, GIF87_SIGNATURE) || startsWith(data, GIF89_SIGNATURE)) {
            return "image/gif";
        }
        if (startsWith(data, BMP_SIGNATURE)) {
            return "image/bmp";
        }
        if (startsWith(data, TIFF_LE_SIGNATURE) || startsWith(data, TIFF_BE_SIGNATURE)) {
            return "image/tiff";
        }
        if (startsWith(data, WMF_SIGNATURE)) {
            return "image/x-wmf";
        }
        // EMF: check for EMR_HEADER record type (1) and reasonable size
        if (data.length >= 44 && startsWith(data, EMF_SIGNATURE)) {
            int recordSize = readInt(data, 4, ByteOrder.LITTLE_ENDIAN);
            if (recordSize >= 88) {
                return "image/x-emf";
            }
        }

        return "application/octet-stream";
    }

    /**
     * Guesses the file extension for image data based on its content.
     *
     * @param data raw image bytes
     * @return extension string without dot (e.g., {@code "jpeg"}, {@code "png"}).
     *         Returns {@code "bin"} if unrecognized.
     */
    public static String guessExtension(byte[] data) {
        String contentType = guessContentType(data);
        return contentTypeExtensions.getOrDefault(contentType, "bin");
    }

    /**
     * Parses image dimensions from binary header data.
     *
     * @param data raw image bytes
     * @return an int array of {@code [width, height]} in pixels.
     *         Returns {@code [0, 0]} if format is unrecognized.
     */
    public static int[] getImageDimensions(byte[] data) {
        if (data == null || data.length < 4) {
            return new int[]{0, 0};
        }

        if (startsWith(data, PNG_SIGNATURE)) {
            return getPngDimensions(data);
        }
        if (startsWith(data, JPEG_SIGNATURE)) {
            return getJpegDimensions(data);
        }
        if (startsWith(data, GIF87_SIGNATURE) || startsWith(data, GIF89_SIGNATURE)) {
            return getGifDimensions(data);
        }
        if (startsWith(data, BMP_SIGNATURE)) {
            return getBmpDimensions(data);
        }
        if (startsWith(data, TIFF_LE_SIGNATURE) || startsWith(data, TIFF_BE_SIGNATURE)) {
            return getTiffDimensions(data);
        }

        return new int[]{0, 0};
    }

    /**
     * Parses PNG IHDR chunk for width and height.
     *
     * @param data raw PNG bytes
     * @return an int array of {@code [width, height]}, or {@code [0, 0]} if data is too short
     */
    public static int[] getPngDimensions(byte[] data) {
        if (data.length < 24) {
            return new int[]{0, 0};
        }
        // IHDR chunk starts at byte 8: length(4) + 'IHDR'(4) + width(4) + height(4)
        int width = readInt(data, 16, ByteOrder.BIG_ENDIAN);
        int height = readInt(data, 20, ByteOrder.BIG_ENDIAN);
        return new int[]{width, height};
    }

    /**
     * Parses JPEG SOF marker for width and height.
     *
     * @param data raw JPEG bytes
     * @return an int array of {@code [width, height]}, or {@code [0, 0]} if not found
     */
    public static int[] getJpegDimensions(byte[] data) {
        int offset = 2; // Skip SOI marker
        int length = data.length;

        while (offset < length - 1) {
            if ((data[offset] & 0xFF) != 0xFF) {
                offset++;
                continue;
            }

            int marker = data[offset + 1] & 0xFF;

            // Skip padding bytes
            if (marker == 0xFF) {
                offset++;
                continue;
            }

            // SOF markers (0xC0 through 0xCF, excluding 0xC4 DHT, 0xC8 JPG, 0xCC DAC)
            if (isSofMarker(marker)) {
                if (offset + 9 < length) {
                    int height = readUnsignedShort(data, offset + 5, ByteOrder.BIG_ENDIAN);
                    int width = readUnsignedShort(data, offset + 7, ByteOrder.BIG_ENDIAN);
                    return new int[]{width, height};
                }
                return new int[]{0, 0};
            }

            // Skip other markers (read segment length)
            if (offset + 3 < length) {
                int segmentLength = readUnsignedShort(data, offset + 2, ByteOrder.BIG_ENDIAN);
                offset += 2 + segmentLength;
            } else {
                break;
            }
        }

        return new int[]{0, 0};
    }

    /**
     * Parses GIF logical screen descriptor for width and height.
     *
     * @param data raw GIF bytes
     * @return an int array of {@code [width, height]}, or {@code [0, 0]} if data is too short
     */
    public static int[] getGifDimensions(byte[] data) {
        if (data.length < 10) {
            return new int[]{0, 0};
        }
        int width = readUnsignedShort(data, 6, ByteOrder.LITTLE_ENDIAN);
        int height = readUnsignedShort(data, 8, ByteOrder.LITTLE_ENDIAN);
        return new int[]{width, height};
    }

    /**
     * Parses BMP info header for width and height.
     *
     * @param data raw BMP bytes
     * @return an int array of {@code [width, height]}, or {@code [0, 0]} if data is too short
     */
    public static int[] getBmpDimensions(byte[] data) {
        if (data.length < 26) {
            return new int[]{0, 0};
        }
        int width = readInt(data, 18, ByteOrder.LITTLE_ENDIAN);
        int height = Math.abs(readInt(data, 22, ByteOrder.LITTLE_ENDIAN)); // Height can be negative (top-down)
        return new int[]{width, height};
    }

    /**
     * Parses TIFF IFD for ImageWidth and ImageLength tags.
     *
     * @param data raw TIFF bytes
     * @return an int array of {@code [width, height]}, or {@code [0, 0]} if data is too short
     */
    public static int[] getTiffDimensions(byte[] data) {
        if (data.length < 8) {
            return new int[]{0, 0};
        }

        // Determine byte order
        ByteOrder endian = (data[0] == 0x49 && data[1] == 0x49)
                ? ByteOrder.LITTLE_ENDIAN
                : ByteOrder.BIG_ENDIAN;

        // Get offset to first IFD
        int ifdOffset = readInt(data, 4, endian);
        if (ifdOffset < 0 || ifdOffset + 2 > data.length) {
            return new int[]{0, 0};
        }

        // Read number of IFD entries
        int numEntries = readUnsignedShort(data, ifdOffset, endian);

        int width = 0;
        int height = 0;
        int offset = ifdOffset + 2;

        for (int i = 0; i < numEntries; i++) {
            if (offset + 12 > data.length) {
                break;
            }
            int tag = readUnsignedShort(data, offset, endian);
            int fieldType = readUnsignedShort(data, offset + 2, endian);
            int valueOffset = offset + 8;

            // Read value based on type (SHORT=3, LONG=4)
            int value;
            if (fieldType == 3) { // SHORT
                value = readUnsignedShort(data, valueOffset, endian);
            } else if (fieldType == 4) { // LONG
                value = readInt(data, valueOffset, endian);
            } else {
                value = 0;
            }

            if (tag == 256) { // ImageWidth
                width = value;
            } else if (tag == 257) { // ImageLength (height)
                height = value;
            }

            if (width != 0 && height != 0) {
                break;
            }

            offset += 12;
        }

        return new int[]{width, height};
    }

    // --- Private helpers ---

    private static boolean startsWith(byte[] data, byte[] prefix) {
        if (data.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSofMarker(int marker) {
        return marker == 0xC0 || marker == 0xC1 || marker == 0xC2 || marker == 0xC3
                || marker == 0xC5 || marker == 0xC6 || marker == 0xC7
                || marker == 0xC9 || marker == 0xCA || marker == 0xCB
                || marker == 0xCD || marker == 0xCE || marker == 0xCF;
    }

    private static int readInt(byte[] data, int offset, ByteOrder order) {
        return ByteBuffer.wrap(data, offset, 4).order(order).getInt();
    }

    private static int readUnsignedShort(byte[] data, int offset, ByteOrder order) {
        return ByteBuffer.wrap(data, offset, 2).order(order).getShort() & 0xFFFF;
    }
}
