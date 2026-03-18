package org.aspose.slides.foss.internal.pptx;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableStyleMappingTest {

    @Test
    void guidToPresetContainsExpectedEntries() {
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{2D5ABB26-0587-4C30-8999-92F81FD0307C}"))
                .isEqualTo("NO_STYLE_NO_GRID");
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{46F890A9-2807-4EBB-B81D-B2AA78EC7F39}"))
                .isEqualTo("DARK_STYLE_2_ACCENT_5_ACCENT_6");
    }

    @Test
    void duplicateGuidKeepsLastValue() {
        // When duplicate GUIDs exist, the last value wins.
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{3B4B98B0-60AC-42C2-AFA5-B58CD77FA1E5}"))
                .isEqualTo("LIGHT_STYLE_1_ACCENT_1");
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{F5AB1C69-6EDB-4FF4-983F-18BD219EF322}"))
                .isEqualTo("MEDIUM_STYLE_2_ACCENT_3");
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{10A1B5D5-9B99-4C35-A422-299274C87F4A}"))
                .isEqualTo("MEDIUM_STYLE_3_ACCENT_6");
        assertThat(TableStyleMapping.GUID_TO_PRESET.get("{69CF1AB2-1976-4502-BF36-3FF5EA218861}"))
                .isEqualTo("MEDIUM_STYLE_4_ACCENT_1");
    }

    @Test
    void presetToGuidIsReverse() {
        // Every value in GUID_TO_PRESET must appear as a key in PRESET_TO_GUID
        for (var entry : TableStyleMapping.GUID_TO_PRESET.entrySet()) {
            String presetName = entry.getValue();
            assertThat(TableStyleMapping.PRESET_TO_GUID).containsKey(presetName);
            // The GUID stored should map back to this preset name
            assertThat(TableStyleMapping.GUID_TO_PRESET.get(TableStyleMapping.PRESET_TO_GUID.get(presetName)))
                    .isEqualTo(presetName);
        }
    }

    @Test
    void presetFromGuidReturnsEmpty() {
        assertThat(TableStyleMapping.presetFromGuid("{00000000-0000-0000-0000-000000000000}")).isEmpty();
    }

    @Test
    void guidFromPresetReturnsEmpty() {
        assertThat(TableStyleMapping.guidFromPreset("NONEXISTENT")).isEmpty();
    }

    @Test
    void mapsAreUnmodifiable() {
        assertThatThrownBy(() -> TableStyleMapping.GUID_TO_PRESET.put("key", "value"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> TableStyleMapping.PRESET_TO_GUID.put("key", "value"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
