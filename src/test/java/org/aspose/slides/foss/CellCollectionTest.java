package org.aspose.slides.foss;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link CellCollection}.
 *
 * <p>Verifies the cell collection behavioral contract.</p>
 */
class CellCollectionTest {

    /** Minimal IBaseSlide stub that returns a fixed presentation. */
    private static IBaseSlide stubSlide(IPresentation pres) {
        return new BaseSlideStub(pres);
    }

    // --- size and get ---

    @Test
    void emptyCollection_hasSizeZero() {
        var collection = new CellCollection();
        assertThat(collection.size()).isEqualTo(0);
    }

    @Test
    void collectionWithCells_reportsCorrectSize() {
        var cells = List.<ICell>of(new CellStub(), new CellStub(), new CellStub());
        var collection = new CellCollection(cells);
        assertThat(collection.size()).isEqualTo(3);
    }

    @Test
    void get_returnsCellAtIndex() {
        var c0 = new CellStub();
        var c1 = new CellStub();
        var collection = new CellCollection(List.of(c0, c1));
        assertThat(collection.get(0)).isSameAs(c0);
        assertThat(collection.get(1)).isSameAs(c1);
    }

    @Test
    void get_outOfBounds_throwsException() {
        var collection = new CellCollection();
        assertThatThrownBy(() -> collection.get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    // --- asICollection ---

    @Test
    void asICollection_returnsListCopy() {
        var c0 = new CellStub();
        var c1 = new CellStub();
        var collection = new CellCollection(List.of(c0, c1));

        List<ICell> list = collection.asICollection();
        assertThat(list).hasSize(2);
        assertThat(list.get(0)).isSameAs(c0);
        assertThat(list.get(1)).isSameAs(c1);
    }

    @Test
    void asICollection_returnsEmptyListForEmptyCollection() {
        var collection = new CellCollection();
        assertThat(collection.asICollection()).isEmpty();
    }

    // --- asIEnumerable ---

    @Test
    void asIEnumerable_isIterable() {
        var c0 = new CellStub();
        var c1 = new CellStub();
        var collection = new CellCollection(List.of(c0, c1));

        int count = 0;
        for (var cell : collection.asIEnumerable()) {
            assertThat(cell).isNotNull();
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    @Test
    void asIEnumerable_emptyCollection() {
        var collection = new CellCollection();
        int count = 0;
        for (var ignored : collection.asIEnumerable()) {
            count++;
        }
        assertThat(count).isEqualTo(0);
    }

    // --- iterator ---

    @Test
    void iterator_iteratesAllCells() {
        var c0 = new CellStub();
        var c1 = new CellStub();
        var collection = new CellCollection(List.of(c0, c1));

        int count = 0;
        for (var cell : collection) {
            assertThat(cell).isNotNull();
            count++;
        }
        assertThat(count).isEqualTo(2);
    }

    // --- slide / presentation ---

    @Test
    void slide_returnsNullByDefault() {
        var collection = new CellCollection();
        assertThat(collection.getSlide()).isNull();
    }

    @Test
    void slide_returnsParentSlideWhenSet() {
        var slide = stubSlide(null);
        var collection = new CellCollection(List.of(), slide);
        assertThat(collection.getSlide()).isSameAs(slide);
    }

    @Test
    void presentation_returnsNullWhenNoParentSlide() {
        var collection = new CellCollection();
        assertThat(collection.getPresentation()).isNull();
    }

    @Test
    void presentation_delegatesToParentSlide() {
        try (var pres = new Presentation()) {
            var slide = stubSlide(pres);
            var collection = new CellCollection(List.of(), slide);
            assertThat(collection.getPresentation()).isSameAs(pres);
        }
    }

    // --- asIPresentationComponent ---

    @Test
    void asIPresentationComponent_returnsSelf() {
        var collection = new CellCollection();
        assertThat(collection.asIPresentationComponent()).isSameAs(collection);
    }

    // --- null cells parameter ---

    @Test
    void constructorWithNullCells_createsEmptyCollection() {
        var collection = new CellCollection(null);
        assertThat(collection.size()).isEqualTo(0);
    }

    // --- Minimal stubs ---

    private static class CellStub implements ICell {
        @Override public double getMarginLeft() { return 0; }
        @Override public void setMarginLeft(double value) {}
        @Override public double getMarginRight() { return 0; }
        @Override public void setMarginRight(double value) {}
        @Override public double getMarginTop() { return 0; }
        @Override public void setMarginTop(double value) {}
        @Override public double getMarginBottom() { return 0; }
        @Override public void setMarginBottom(double value) {}
        @Override public TextAnchorType getTextAnchorType() { return TextAnchorType.NOT_DEFINED; }
        @Override public void setTextAnchorType(TextAnchorType value) {}
        @Override public TextVerticalType getTextVerticalType() { return TextVerticalType.NOT_DEFINED; }
        @Override public void setTextVerticalType(TextVerticalType value) {}
        @Override public boolean isAnchorCenter() { return false; }
        @Override public void setAnchorCenter(boolean value) {}
        @Override public ITextFrame getTextFrame() { return null; }
        @Override public boolean isMergedCell() { return false; }
        @Override public int getColSpan() { return 1; }
        @Override public int getRowSpan() { return 1; }
        @Override public ITable getTable() { return null; }
        @Override public int getFirstRowIndex() { return 0; }
        @Override public int getFirstColumnIndex() { return 0; }
        @Override public double getOffsetX() { return 0; }
        @Override public double getOffsetY() { return 0; }
        @Override public double getWidth() { return 0; }
        @Override public double getHeight() { return 0; }
        @Override public double getMinimalHeight() { return 0; }
        @Override public IRow getFirstRow() { return null; }
        @Override public IColumn getFirstColumn() { return null; }
        @Override public ICellFormat getCellFormat() { return null; }
        @Override public IBaseSlide getSlide() { return null; }
        @Override public IPresentation getPresentation() { return null; }
        @Override public ISlideComponent getAsISlideComponent() { return null; }
        @Override public IPresentationComponent getAsIPresentationComponent() { return null; }
    }

    private static class BaseSlideStub implements IBaseSlide {
        private final IPresentation presentation;
        BaseSlideStub(IPresentation presentation) { this.presentation = presentation; }
        @Override public String getName() { return "stub"; }
        @Override public void setName(String name) {}
        @Override public int getSlideId() { return 0; }
        @Override public IShapeCollection getShapes() { return null; }
        @Override public IBaseSlide getSlide() { return this; }
        @Override public IPresentationComponent asIPresentationComponent() { return this; }
        @Override public IPresentation getPresentation() { return presentation; }
    }
}
