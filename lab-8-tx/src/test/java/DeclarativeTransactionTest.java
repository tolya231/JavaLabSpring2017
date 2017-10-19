import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import lab.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class DeclarativeTransactionTest extends JdbcTest {

  @Autowired
  private CountryService countryService;

  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  @DirtiesContext
  public void testRequiredPropagationInsideTransaction() {
    assertNotNull(countryService.getAllCountriesInsideTransaction(Propagation.REQUIRED));
  }

  @Test
  @DirtiesContext
  public void testRequiredPropagationWithoutTransaction() {
    assertNotNull(countryService.getAllCountriesRequired());
  }

  @Test
  @DirtiesContext
  public void testMandatoryPropagationWithoutTransaction() {
    try {
      countryService.getAllCountriesMandatory();
    } catch (Exception e) {
      assertTrue(e instanceof IllegalTransactionStateException);
      e.printStackTrace();
    }
  }

  @Test
  @DirtiesContext
  public void testMandatoryPropagationInsideTransaction() {
    assertNotNull(countryService.getAllCountriesInsideTransaction(Propagation.MANDATORY));
  }
}