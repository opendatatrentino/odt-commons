package eu.trentorise.opendata.commons.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.commons.validation.Ref;

public class RefTest {

    @BeforeClass
    public static void setUpClass() {
        TodConfig.init(RefTest.class);
    }

    @Test
    public void testRef() {
        assertEquals("a", Ref.ofDocumentId("a")
                             .getDocumentId());
        assertEquals("a", Ref.ofPath("a")
                             .getTracePath());
        assertEquals("", Ref.ofPath("a")
                            .getDocumentId());

        assertEquals("a", Ref.ofPath("a")
                             .uri());
        assertEquals("a", Ref.ofDocumentId("a")
                             .uri());
        try {
            Ref.of()
               .uri();
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalStateException ex) {

        }

        assertEquals("a#b", Ref.builder()
                               .setDocumentId("a")
                               .setTracePath("b")
                               .build()
                               .uri());
        try {
            Ref.builder().setPhysicalColumn(-2).build();
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalStateException ex) {

        }

        try {
            Ref.builder().setPhysicalRow(-2).build();
            Assert.fail("Shouldn't arrive here!");
        } catch (IllegalStateException ex) {

        }
        
        Ref.builder().setPhysicalColumn(-1).build();
        Ref.builder().setPhysicalRow(-1).build();
        
        Ref.builder().setPhysicalColumn(0).build();
        Ref.builder().setPhysicalRow(0).build();
        
        
    }
}
