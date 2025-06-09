package com.cuizhanming.multidatasource;

import com.cuizhanming.multidatasource.gcp.controller.CredentialController;
import com.cuizhanming.multidatasource.server.AbstractEmbeddedCassandraTest;
import com.cuizhanming.multidatasource.gcp.controller.CredentialCassandraController;
import com.cuizhanming.multidatasource.gcp.model.Credential;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests extends AbstractEmbeddedCassandraTest {

	@Autowired
	private CredentialController credentialController;


	@Autowired
	private CredentialCassandraController credentialCassandraController;

	@Before
	public void setup(){
	}

	@Test
	public void contextLoads() {
		assertThat(credentialController).isNotNull();
		Credential credential = new Credential().setAccessKey("ACCESS_KEY").setSecretKey("ACCESS_SECRET_KEY");
		Credential savedCredential = credentialController.createCredential(credential);
		System.out.println(savedCredential.getId());
		assertThat(savedCredential.getId()).isNotBlank();
	}

	@Test
	public void contextLoadsCassandra() {
		assertThat(credentialCassandraController).isNotNull();
		Credential credential = new Credential().setAccessKey("ACCESS_KEY").setSecretKey("ACCESS_SECRET_KEY");
		Credential savedCredential = credentialCassandraController.createCredential(credential);
		System.out.println(savedCredential.getId());
		assertThat(savedCredential.getId()).isNotBlank();
	}
}
