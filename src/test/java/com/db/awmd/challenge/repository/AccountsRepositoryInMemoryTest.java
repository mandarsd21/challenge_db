package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AccountUpdate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountsRepositoryInMemoryTest {

    private AccountsRepository accountsRepository;

    @Before
    public void setUp(){
        accountsRepository = new AccountsRepositoryInMemory();
    }

    @Test
    public void updateAccountsBatch_should_updateAllAccounts() throws Exception {

        accountsRepository.createAccount(new Account("101", BigDecimal.ZERO));
        accountsRepository.createAccount(new Account("102", new BigDecimal("150.20")));

        List<AccountUpdate> accountUpdates = Arrays.asList(
                new AccountUpdate("101", BigDecimal.ZERO),
                new AccountUpdate("102", new BigDecimal("-50"))
        );

        accountsRepository.updateAccounts(accountUpdates);
        assertBalance("101", BigDecimal.ZERO);
        assertBalance("102", new BigDecimal("100.20"));
    }

    private void assertBalance(String accountId, BigDecimal balance){
        assertThat(accountsRepository.getAccount(accountId).getBalance()).isEqualTo(balance);
    }

}