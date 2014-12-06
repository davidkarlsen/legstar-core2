package com.legstar.base.converter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.legstar.base.context.CobolContext;
import com.legstar.base.context.EbcdicCobolContext;
import com.legstar.base.converter.Cob2ObjectValidator;
import com.legstar.base.type.gen.Ardo01RecordFactory;
import com.legstar.base.type.gen.CustomerDataFactory;
import com.legstar.base.type.gen.Flat01RecordFactory;
import com.legstar.base.type.gen.Flat02RecordFactory;
import com.legstar.base.type.gen.Rdef03RecordFactory;
import com.legstar.base.type.gen.Stru01RecordFactory;
import com.legstar.base.type.gen.Stru03RecordFactory;
import com.legstar.base.utils.HexUtils;
import com.legstar.base.visitor.Rdef03ObjectFromHostChoiceStrategy;

public class Cob2ObjectValidatorTest {

    private CobolContext cobolContext;

    @Before
    public void setUp() {
        cobolContext = new EbcdicCobolContext();
    }

    @Test
    public void tesFlat01Valid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F4F3D5C1D4C5F0F0F0F0F4F3404040404040404040400215000F"),
                0);
        visitor.visit(Flat01RecordFactory.create());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesFlat01Invalid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0FFF0F4F3D5C1D4C5F0F0F0F0F4F3404040404040404040400215000F"),
                0);
        visitor.visit(Flat01RecordFactory.create());
        assertFalse(visitor.isValid());

        visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F4F3D5C101C5F0F0F0F0F4F3404040404040404040400215000F"),
                0);
        visitor.visit(Flat01RecordFactory.create());
        assertFalse(visitor.isValid());


        visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F4F3D5C1D4C5F0F0F0F0F4F3404040404040404040400215A00F"),
                0);
        visitor.visit(Flat01RecordFactory.create());
        assertFalse(visitor.isValid());
    }

    @Test
    public void tesFlat01WithStopField() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F4F3D5C1D4C5F0F0F0F0F4F3404040404040404040400215A00F"),
                0, "comName");
        visitor.visit(Flat01RecordFactory.create());
        assertTrue(visitor.isValid());

        visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F4F3D5C1D4C5F0F0F0F0F4F3404040404040404040400215A00F"),
                0, "comAmount");
        visitor.visit(Flat01RecordFactory.create());
        assertFalse(visitor.isValid());

    }


    @Test
    public void tesFlat02Valid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F003E001F0014000F000C"),
                0);
        visitor.visit(Flat02RecordFactory.create());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesFlat02InValid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F003E0F1F0014000F000C"),
                0);
        visitor.visit(Flat02RecordFactory.create());
        assertFalse(visitor.isValid());
        assertEquals(32, visitor.getLastPos());

    }

    @Test
    public void tesStru01Valid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F003EC1C2"),
                0);
        visitor.visit(Stru01RecordFactory.createStru01Record());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesStru01InValid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F023EC1C2"),
                0);
        visitor.visit(Stru01RecordFactory.createStru01Record());
        assertFalse(visitor.isValid());
        assertEquals(30, visitor.getLastPos());

    }

    @Test
    public void tesStru03Valid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F003EC1C2001FC1C20014C1C2000FC1C2000CC1C2"),
                0);
        visitor.visit(Stru03RecordFactory.createStru03Record());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesStru03InValid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400310000F003EC1C2001FC1C20014C1C20F0FC1C2000CC1C2"),
                0);
        visitor.visit(Stru03RecordFactory.createStru03Record());
        assertFalse(visitor.isValid());
        assertEquals(42, visitor.getLastPos());

    }

    @Test
    public void tesArdo01Valid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400005000000000023556C000000000023656C000000000023756C000000000023856C000000000023956C"),
                0);
        visitor.visit(Ardo01RecordFactory.createArdo01Record());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesArdo01InValidCounterOfOfRange() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400006000000000023556C000000000023656C000000000023756C000000000023856C000000000023956C"),
                0);
        visitor.visit(Ardo01RecordFactory.createArdo01Record());
        assertFalse(visitor.isValid());
        assertEquals(26, visitor.getLastPos());

    }

    @Test
    public void tesArdo01InValidComp3() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F6F2D5C1D4C5F0F0F0F0F6F2404040404040404040400001000000000023F56C"),
                0);
        visitor.visit(Ardo01RecordFactory.createArdo01Record());
        assertFalse(visitor.isValid());
        assertEquals(28, visitor.getLastPos());

    }


    @Test
    public void tesRdef03ValidDefaultStrategyFirstAlternative() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("0001C1C2C3C4404040404040"),
                0);
        visitor.visit(Rdef03RecordFactory.createRdef03Record());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesRdef03ValidDefaultStrategySecondAlternative() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("00010250000F"),
                0);
        visitor.visit(Rdef03RecordFactory.createRdef03Record());
        assertTrue(visitor.isValid());

    }

    @Test
    public void tesRdef03ValidCustomStrategy() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("0002F1F2F3F4F50000000000"),
                0, null, new Rdef03ObjectFromHostChoiceStrategy());
        visitor.visit(Rdef03RecordFactory.createRdef03Record());
        assertTrue(visitor.isValid());

    }


    @Test
    public void tesCustdatValid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F0F1D1D6C8D540E2D4C9E3C840404040404040404040C3C1D4C2D9C9C4C7C540E4D5C9E5C5D9E2C9E3E8F4F4F0F1F2F5F6F500000002F1F061F0F461F1F1000000000023556C5C5C5C5C5C5C5C5C5CF1F061F0F461F1F1000000000023556C5C5C5C5C5C5C5C5C5C"),
                0);
        visitor.visit(CustomerDataFactory.create());
        assertTrue(visitor.isValid());
        assertEquals(108, visitor.getLastPos());

    }

    @Test
    public void tesCustdatInvalid() {

        Cob2ObjectValidator visitor = new Cob2ObjectValidator(cobolContext,
                HexUtils.decodeHex("F0F0F0F0F0F1D1D6C8D540E2D4C9E3C840404040404040404040C3C1D4C2D9C9C4C7C540E4D5C9E5C5D9E2C9E3E8F4F4F0F1F2F5F6F500000002F1F061F0F461F1F1000000000023556C5C5C5C5C5C5C5C5C5CF1F061F0F461F1F100000000002355675C5C5C5C5C5C5C5C5C"),
                0);
        visitor.visit(CustomerDataFactory.create());
        assertFalse(visitor.isValid());
        assertEquals(91, visitor.getLastPos());

    }

}