package org.aspose.slides.foss.internal.pptx;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Mapping between OOXML table style GUIDs and {@code TableStylePreset} enum
 * name strings.
 *
 * <p>Provides two unmodifiable lookup maps:
 * <ul>
 *   <li>{@link #GUID_TO_PRESET} — GUID string → preset enum name</li>
 *   <li>{@link #PRESET_TO_GUID} — preset enum name → GUID string</li>
 * </ul>
 *
 * <p>Where a GUID appears more than once in the OOXML specification the later
 * (higher-priority) mapping takes precedence.</p>
 */
public final class TableStyleMapping {

    private TableStyleMapping() {
        // utility class
    }

    /**
     * Unmodifiable map from OOXML table-style GUID to preset enum name.
     *
     * <p>Duplicate GUID keys are resolved in favour of the last entry,
     * preserving insertion order so later entries override earlier ones.</p>
     */
    public static final Map<String, String> GUID_TO_PRESET;

    /**
     * Unmodifiable reverse map from preset enum name to OOXML table-style GUID.
     */
    public static final Map<String, String> PRESET_TO_GUID;

    static {
        // Use LinkedHashMap to preserve insertion order.
        // Where a GUID key is repeated the later put() wins.
        var guid = new LinkedHashMap<String, String>();
        guid.put("{2D5ABB26-0587-4C30-8999-92F81FD0307C}", "NO_STYLE_NO_GRID");
        guid.put("{5940675A-B579-460E-94D1-54222C63F5DA}", "NO_STYLE_TABLE_GRID");
        guid.put("{3B4B98B0-60AC-42C2-AFA5-B58CD77FA1E5}", "THEMED_STYLE_1_ACCENT_1");
        guid.put("{0E3FDE45-AF77-4B5C-9715-49D594BDF05E}", "THEMED_STYLE_1_ACCENT_2");
        guid.put("{C083E6E3-FA7D-4D7B-A595-EF9225AFEA82}", "THEMED_STYLE_1_ACCENT_3");
        guid.put("{D27102A9-8310-4765-A935-A1911B00CA55}", "THEMED_STYLE_1_ACCENT_4");
        guid.put("{5FD0F851-EC5A-4D38-B0AD-8093EC10F338}", "THEMED_STYLE_1_ACCENT_5");
        guid.put("{68D230F3-CF80-4859-8CE7-A43EE81993B5}", "THEMED_STYLE_1_ACCENT_6");
        guid.put("{7E9639D4-E3E2-4D34-9284-5A2195B3D0D7}", "THEMED_STYLE_2_ACCENT_1");
        guid.put("{F5AB1C69-6EDB-4FF4-983F-18BD219EF322}", "THEMED_STYLE_2_ACCENT_2");
        guid.put("{A49D2060-B5AB-4B49-B4F8-6B4FB68B3F6C}", "THEMED_STYLE_2_ACCENT_3");
        guid.put("{7BFB00D3-D3B8-4EA4-B537-2B4AD1B4F21F}", "THEMED_STYLE_2_ACCENT_4");
        guid.put("{3147F4FF-CEAB-4984-AB22-6B4E1A3A4C28}", "THEMED_STYLE_2_ACCENT_5");
        guid.put("{B39EC027-3F22-4C18-AD4F-5C40B1F15098}", "THEMED_STYLE_2_ACCENT_6");
        guid.put("{9D7B26C5-4107-4FEC-AEDC-1716B250A1EF}", "LIGHT_STYLE1");
        // Duplicate GUIDs — these overwrite the THEMED_STYLE_1_* entries above
        guid.put("{3B4B98B0-60AC-42C2-AFA5-B58CD77FA1E5}", "LIGHT_STYLE_1_ACCENT_1");
        guid.put("{0E3FDE45-AF77-4B5C-9715-49D594BDF05E}", "LIGHT_STYLE_1_ACCENT_2");
        guid.put("{C083E6E3-FA7D-4D7B-A595-EF9225AFEA82}", "LIGHT_STYLE_1_ACCENT_3");
        guid.put("{D27102A9-8310-4765-A935-A1911B00CA55}", "LIGHT_STYLE_1_ACCENT_4");
        guid.put("{5FD0F851-EC5A-4D38-B0AD-8093EC10F338}", "LIGHT_STYLE_1_ACCENT_5");
        guid.put("{68D230F3-CF80-4859-8CE7-A43EE81993B5}", "LIGHT_STYLE_1_ACCENT_6");
        guid.put("{69012ECD-51FC-41F1-AA8D-1B2483CD663E}", "LIGHT_STYLE2");
        guid.put("{69CF1AB2-1976-4502-BF36-3FF5EA218861}", "LIGHT_STYLE_2_ACCENT_1");
        guid.put("{72833802-FEF1-4C79-8D5D-14CF1EAFBEc2}", "LIGHT_STYLE_2_ACCENT_2");
        guid.put("{F2DE63D5-997A-4646-A377-4702673A728D}", "LIGHT_STYLE_2_ACCENT_3");
        guid.put("{17292A2E-F333-43FB-9621-5CBBE7FDCDCB}", "LIGHT_STYLE_2_ACCENT_4");
        guid.put("{5A111915-BE36-4E01-A7E5-04B1672EAD32}", "LIGHT_STYLE_2_ACCENT_5");
        guid.put("{912C8C85-51F0-491E-9774-3900AFEF0FD7}", "LIGHT_STYLE_2_ACCENT_6");
        guid.put("{616DA210-FB5B-4158-B5E0-FEB733F419BA}", "LIGHT_STYLE3");
        guid.put("{BC89EF96-8CEA-46FF-86C4-4CE0E7609802}", "LIGHT_STYLE_3_ACCENT_1");
        guid.put("{5DA37D80-6434-44D0-A028-1B22A696006F}", "LIGHT_STYLE_3_ACCENT_2");
        guid.put("{8799B23B-EC83-4686-B30A-512413B5E67A}", "LIGHT_STYLE_3_ACCENT_3");
        guid.put("{ED083AE6-46FA-4A59-8FB0-9F97EB10719F}", "LIGHT_STYLE_3_ACCENT_4");
        guid.put("{BDBED569-4797-4B45-8F2D-6FED3D3028C5}", "LIGHT_STYLE_3_ACCENT_5");
        guid.put("{E8B1032C-EA38-4F05-BA0D-38AFFFC7BED3}", "LIGHT_STYLE_3_ACCENT_6");
        guid.put("{793D81CF-94F2-401A-BA57-92F5A7AA700F}", "MEDIUM_STYLE1");
        guid.put("{B301B821-A1FF-4177-AEE7-76D212191A09}", "MEDIUM_STYLE_1_ACCENT_1");
        guid.put("{9DCAF9ED-07DC-4A11-8D7F-57B35C25682E}", "MEDIUM_STYLE_1_ACCENT_2");
        guid.put("{1FECB4D8-DB02-4DC6-A0A2-4F2EBAE1DC90}", "MEDIUM_STYLE_1_ACCENT_3");
        guid.put("{1E171933-4619-4E11-9A3F-F7608DF75F80}", "MEDIUM_STYLE_1_ACCENT_4");
        guid.put("{FABFCF23-3B69-468F-B69F-88F6DE6A72F2}", "MEDIUM_STYLE_1_ACCENT_5");
        guid.put("{10A1B5D5-9B99-4C35-A422-299274C87F4A}", "MEDIUM_STYLE_1_ACCENT_6");
        guid.put("{073A0DAA-6AF3-43AB-8588-CEC1D06C72B9}", "MEDIUM_STYLE2");
        guid.put("{5C22544A-7EE6-4342-B048-85BDC9FD1C3A}", "MEDIUM_STYLE_2_ACCENT_1");
        guid.put("{21E4AEA4-8DFA-4A89-87EB-49C32662AFE0}", "MEDIUM_STYLE_2_ACCENT_2");
        // Duplicate GUID — overwrites THEMED_STYLE_2_ACCENT_2
        guid.put("{F5AB1C69-6EDB-4FF4-983F-18BD219EF322}", "MEDIUM_STYLE_2_ACCENT_3");
        guid.put("{00A15C55-8517-42AA-B614-E9B94910E393}", "MEDIUM_STYLE_2_ACCENT_4");
        guid.put("{7DF18680-E054-41AD-8BC1-D1AEF088D02A}", "MEDIUM_STYLE_2_ACCENT_5");
        guid.put("{93296810-A885-4BE3-A3E7-6D5BEEA58F35}", "MEDIUM_STYLE_2_ACCENT_6");
        guid.put("{8EC20E35-A176-4012-BC5E-935CFFF8708E}", "MEDIUM_STYLE3");
        guid.put("{6E25E649-3F16-4E02-A733-19D2CDBF48F0}", "MEDIUM_STYLE_3_ACCENT_1");
        guid.put("{85BE263C-DBD7-4A20-BB59-AAB30ACAA65A}", "MEDIUM_STYLE_3_ACCENT_2");
        guid.put("{EB344D84-9AFB-497E-A393-DC336BA19D2E}", "MEDIUM_STYLE_3_ACCENT_3");
        guid.put("{EB9631B5-78F2-41C9-869B-9F39066F8104}", "MEDIUM_STYLE_3_ACCENT_4");
        guid.put("{C8F8D42C-1E25-488A-AEF0-B7A485FBBA82}", "MEDIUM_STYLE_3_ACCENT_5");
        // Duplicate GUID — overwrites MEDIUM_STYLE_1_ACCENT_6
        guid.put("{10A1B5D5-9B99-4C35-A422-299274C87F4A}", "MEDIUM_STYLE_3_ACCENT_6");
        guid.put("{D7AC3CCA-C797-4891-BE02-D94E43425B78}", "MEDIUM_STYLE4");
        // Duplicate GUID — overwrites LIGHT_STYLE_2_ACCENT_1
        guid.put("{69CF1AB2-1976-4502-BF36-3FF5EA218861}", "MEDIUM_STYLE_4_ACCENT_1");
        guid.put("{8A107856-5554-42FB-B03E-39F5DBC370BA}", "MEDIUM_STYLE_4_ACCENT_2");
        guid.put("{0505E3EF-67EA-436B-97B2-0124C06EBD24}", "MEDIUM_STYLE_4_ACCENT_3");
        guid.put("{C4B1156A-380E-4F78-BDF5-A137D656787B}", "MEDIUM_STYLE_4_ACCENT_4");
        guid.put("{22838BEF-8BB2-4498-84A7-C5851F593DF1}", "MEDIUM_STYLE_4_ACCENT_5");
        guid.put("{16D9F66E-5EB9-4882-86FB-DCBF35E3C3E4}", "MEDIUM_STYLE_4_ACCENT_6");
        guid.put("{E8034E78-7F5D-4C2E-B375-FC64B27BC917}", "DARK_STYLE1");
        guid.put("{125E5076-3810-47DD-B79F-674D7AD40C01}", "DARK_STYLE_1_ACCENT_1");
        guid.put("{37CE84F3-28C3-443E-9E96-99CF82512B78}", "DARK_STYLE_1_ACCENT_2");
        guid.put("{D03447BB-5D67-496B-8275-B603D4174691}", "DARK_STYLE_1_ACCENT_3");
        guid.put("{E929F9F4-4A8F-4326-A1B4-22849713DDAB}", "DARK_STYLE_1_ACCENT_4");
        guid.put("{8FD4443E-F989-4FC4-A0C8-D5A2AF1F390B}", "DARK_STYLE_1_ACCENT_5");
        guid.put("{AF606853-7671-496A-8E4F-DF71F8EC918B}", "DARK_STYLE_1_ACCENT_6");
        guid.put("{5202B0CA-FC54-4496-8BCA-5EF66A818D29}", "DARK_STYLE2");
        guid.put("{0660B408-B3CF-4A94-85FC-2B1E0A45F4A2}", "DARK_STYLE_2_ACCENT_1_ACCENT_2");
        guid.put("{91EBBBCC-DAD2-459C-BE2E-F6DE35CF9A28}", "DARK_STYLE_2_ACCENT_3_ACCENT_4");
        guid.put("{46F890A9-2807-4EBB-B81D-B2AA78EC7F39}", "DARK_STYLE_2_ACCENT_5_ACCENT_6");

        GUID_TO_PRESET = Collections.unmodifiableMap(guid);

        // Reverse mapping: preset name -> GUID (last GUID wins for each preset)
        var preset = new LinkedHashMap<String, String>();
        for (var entry : guid.entrySet()) {
            preset.put(entry.getValue(), entry.getKey());
        }
        PRESET_TO_GUID = Collections.unmodifiableMap(preset);
    }

    /**
     * Looks up a preset enum name by its OOXML table-style GUID.
     *
     * @param guid the table-style GUID (e.g. {@code "{2D5ABB26-0587-4C30-8999-92F81FD0307C}"})
     * @return the matching preset name, or empty if not recognized
     */
    public static Optional<String> presetFromGuid(String guid) {
        return Optional.ofNullable(GUID_TO_PRESET.get(guid));
    }

    /**
     * Looks up an OOXML table-style GUID by its preset enum name.
     *
     * @param preset the preset enum name (e.g. {@code "NO_STYLE_NO_GRID"})
     * @return the matching GUID, or empty if not recognized
     */
    public static Optional<String> guidFromPreset(String preset) {
        return Optional.ofNullable(PRESET_TO_GUID.get(preset));
    }
}
