package accounts.web;

import accounts.AccountManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rewards.internal.account.Account;

// TODO-06: Get yourself familiarized with various testing utility classes
// - Uncomment the import statements below
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO-07: Replace @ExtendWith(SpringExtension.class) with the following annotation
// - @WebMvcTest(AccountController.class) // includes @ExtendWith(SpringExtension.class)
//@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerBootTests {

	// TODO-08: Autowire MockMvc bean
	@Autowired
	private MockMvc mockMvc;

	// TODO-09: Create AccountManager mock bean using @MockBean annotation
	@MockBean
	private AccountManager accountManager;

	// TODO-10: Write positive test for GET request for an account
	// - Uncomment the code and run the test and verify it succeeds
	@Test
	public void accountDetails() throws Exception {

		given(accountManager.getAccount(0L))
				.willReturn(new Account("1234567890", "John Doe"));

		mockMvc.perform(get("/accounts/0"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(jsonPath("name").value("John Doe"))
			   .andExpect(jsonPath("number").value("1234567890"));

		verify(accountManager).getAccount(0L);

	}

	// TODO-11: Write negative test for GET request for a non-existent account
	// - Uncomment the "given" and "verify" statements
	// - Write code between the "given" and "verify" statements
	// - Run the test and verify it succeeds
	@Test
	public void accountDetailsFail() throws Exception {

		given(accountManager.getAccount(any(Long.class)))
				.willThrow(new IllegalArgumentException("No such account with id " + 0L));

		// (Write code here)
		// - Use mockMvc to perform HTTP Get operation using "/accounts/9999"
        //   as a non-existent account URL
		// - Verify that the HTTP response status is 404
		mockMvc.perform(get("/accounts/9999"))
				.andExpect(status().isNotFound());

		verify(accountManager).getAccount(any(Long.class));

	}

    // TODO-12: Write test for `POST` request for an account
	// - Uncomment Java code below
	// - Write code between the "given" and "verify" statements
	// - Run the test and verify it succeeds
	@Test
	public void createAccount() throws Exception {

		Account testAccount = new Account("1234512345", "Mary Jones");
		testAccount.setEntityId(21L);

		given(accountManager.save(any(Account.class)))
				.willReturn(testAccount);

		// (Write code here)
		// Use mockMvc to perform HTTP Post operation to "/accounts"
		// - Set the request content type to APPLICATION_JSON
		// - Set the request content with Json string of the "testAccount"
		//   (Use "asJsonString" method below to convert the "testAccount"
		//   object into Json string)
		// - Verify that the response status is 201
		// - Verify that the response "Location" header contains "http://localhost/accounts/21"
		mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(testAccount)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/accounts/21"));

		verify(accountManager).save(any(Account.class));

	}

	@Test
	@DisplayName("Test for get all accounts api")
	public void testGetAllAccounts() throws Exception {
		// given
		Account account1 = new Account("1234567890", "John Doe");
		Account account2 = new Account("1234567891", "Jane Doe");
		List accounts = List.of(account1, account2);
		given(accountManager.getAllAccounts()).willReturn(accounts);

		// when and then
		mockMvc.perform(get("/accounts"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("John Doe"))
				.andExpect(jsonPath("$[0].number").value("1234567890"))
				.andExpect(jsonPath("$[1].name").value("Jane Doe"))
				.andExpect(jsonPath("$[1].number").value("1234567891"));

		verify(accountManager).getAllAccounts();
	}

	@Test
	@DisplayName("Test for get beneficiary api")
	public void testGetBeneficiary() throws Exception {
		// given
		Account account = new Account("1234567890", "John Doe");
		account.addBeneficiary("Jane Doe");
		given(accountManager.getAccount(0L)).willReturn(account);

		// when and then
		mockMvc.perform(get("/accounts/0/beneficiaries/Jane Doe"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("name").value("Jane Doe"))
				.andExpect(jsonPath("allocationPercentage").value("1.0"));

		verify(accountManager).getAccount(0L);
	}

	@Test
	@DisplayName("Test for get non-existent beneficiary api")
	public void testGetNonExistentBeneficiary() throws Exception {
		// given
		Account account = new Account("1234567890", "John Doe");
		account.addBeneficiary("Jane Doe");
		given(accountManager.getAccount(0L)).willReturn(account);

		// when and then
		mockMvc.perform(get("/accounts/0/beneficiaries/Non Existent"))
				.andExpect(status().isNotFound());

		verify(accountManager).getAccount(0L);
	}

	@Test
	@DisplayName("Test for add beneficiary api")
	public void testAddBeneficiary() throws Exception {
		// given
		Account account = new Account("1234567890", "John Doe");
		given(accountManager.getAccount(0L)).willReturn(account);

		// when and then
		mockMvc.perform(post("/accounts/0/beneficiaries")
				.contentType(MediaType.TEXT_PLAIN)
				.content("Jane Doe"))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/accounts/0/beneficiaries/Jane%20Doe"));

		verify(accountManager).addBeneficiary(0L, "Jane Doe");
	}

	@Test
	@DisplayName("Test for remove beneficiary api")
	public void testRemoveBeneficiary() throws Exception {
		// given
		Account account = new Account("1234567890", "John Doe");
		account.addBeneficiary("Jane Doe");
		given(accountManager.getAccount(0L)).willReturn(account);

		// when and then
		mockMvc.perform(delete("/accounts/0/beneficiaries/Jane Doe"))
				.andExpect(status().isNoContent());

		verify(accountManager).removeBeneficiary(0L, "Jane Doe", Map.of());
	}

	@Test
	@DisplayName("Test for remove non-existent beneficiary api")
	public void testRemoveNonExistentBeneficiary() throws Exception {
		// given
		Account account = new Account("1234567890", "John Doe");
		account.addBeneficiary("Jane Doe");
		given(accountManager.getAccount(0L)).willReturn(account);

		// when and then
		mockMvc.perform(delete("/accounts/0/beneficiaries/Non Existent"))
				.andExpect(status().isNotFound());
	}


    // Utility class for converting an object into JSON string
	protected static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO-13 (Optional): Experiment with @MockBean vs @Mock
	// - Change `@MockBean` to `@Mock` for the `AccountManager dependency above
	// - Run the test and observe a test failure
	// - Change it back to `@MockBean`

}
