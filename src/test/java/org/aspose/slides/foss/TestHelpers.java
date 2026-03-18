package org.aspose.slides.foss;

import org.aspose.slides.foss.export.SaveFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Shared test helpers.
 *
 * <p>Provides round-trip save/reload and minimal PNG generation utilities
 * used across the integration tests.</p>
 */
public final class TestHelpers {

    private TestHelpers() {
        // utility class
    }

    /**
     * Saves a {@link Presentation} to a temporary PPTX file, disposes it,
     * and reopens the file as a new {@link Presentation}.
     *
     * <p>Simulates a round-trip save/reload cycle.</p>
     *
     * @param pres    the presentation to round-trip
     * @param tempDir a temporary directory (from {@code @TempDir})
     * @return a freshly opened presentation loaded from the saved file
     * @throws IOException if saving or loading fails
     */
    public static Presentation roundTripFile(Presentation pres, Path tempDir) throws IOException {
        Path path = tempDir.resolve("roundtrip.pptx");
        pres.save(path.toString(), SaveFormat.PPTX);
        pres.dispose();
        return new Presentation(path.toString());
    }

    /**
     * Generates a minimal valid 1&times;1 PNG image with the given RGB colour.
     *
     * <p>Creates a minimal PNG suitable for use in image-related tests.</p>
     *
     * @param r red component (0-255)
     * @param g green component (0-255)
     * @param b blue component (0-255)
     * @return a byte array containing a valid PNG file
     */
    public static byte[] createTestPng(int r, int g, int b) {
        try {
            // PNG signature
            byte[] header = {
                (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
            };

            // IHDR data: width=1, height=1, bitDepth=8, colorType=2 (RGB), compression=0, filter=0, interlace=0
            ByteBuffer ihdrData = ByteBuffer.allocate(13);
            ihdrData.order(ByteOrder.BIG_ENDIAN);
            ihdrData.putInt(1);  // width
            ihdrData.putInt(1);  // height
            ihdrData.put((byte) 8);   // bit depth
            ihdrData.put((byte) 2);   // color type (RGB)
            ihdrData.put((byte) 0);   // compression
            ihdrData.put((byte) 0);   // filter
            ihdrData.put((byte) 0);   // interlace

            // Raw pixel data: filter byte (0) + RGB
            byte[] raw = {0, (byte) r, (byte) g, (byte) b};

            // Compress raw data with zlib
            ByteArrayOutputStream compressedOut = new ByteArrayOutputStream();
            try (var deflaterStream = new DeflaterOutputStream(compressedOut, new Deflater(Deflater.DEFAULT_COMPRESSION))) {
                deflaterStream.write(raw);
            }
            byte[] idat = compressedOut.toByteArray();

            // Build full PNG
            ByteArrayOutputStream png = new ByteArrayOutputStream();
            png.write(header);
            png.write(pngChunk(new byte[]{0x49, 0x48, 0x44, 0x52}, ihdrData.array()));  // IHDR
            png.write(pngChunk(new byte[]{0x49, 0x44, 0x41, 0x54}, idat));              // IDAT
            png.write(pngChunk(new byte[]{0x49, 0x45, 0x4E, 0x44}, new byte[0]));       // IEND

            return png.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test PNG", e);
        }
    }

    /**
     * Generates a minimal valid 1&times;1 red PNG image.
     *
     * @return a byte array containing a valid PNG file
     */
    public static byte[] createTestPng() {
        return createTestPng(255, 0, 0);
    }

    /**
     * Builds a single PNG chunk.
     *
     * @param chunkType 4-byte chunk type (e.g. "IHDR")
     * @param data      chunk data
     * @return the full chunk bytes (length + type + data + CRC)
     */
    private static byte[] pngChunk(byte[] chunkType, byte[] data) {
        ByteBuffer buf = ByteBuffer.allocate(4 + 4 + data.length + 4);
        buf.order(ByteOrder.BIG_ENDIAN);

        // Length of data
        buf.putInt(data.length);

        // Type + Data (CRC is computed over type + data)
        byte[] typeAndData = new byte[chunkType.length + data.length];
        System.arraycopy(chunkType, 0, typeAndData, 0, chunkType.length);
        System.arraycopy(data, 0, typeAndData, chunkType.length, data.length);

        buf.put(typeAndData);

        // CRC32
        CRC32 crc = new CRC32();
        crc.update(typeAndData);
        buf.putInt((int) (crc.getValue() & 0xFFFFFFFFL));

        return buf.array();
    }
}
