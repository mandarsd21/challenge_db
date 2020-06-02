package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.NotEnoughFundsException;
import com.db.awmd.challenge.exception.TransferBetweenSameAccountException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;


public class TransferValidatorImplTest {

    private TransferValidator transferValidator;

    @Before
    public void setUp() {
        transferValidator = new TransferValidatorImpl();
    }

    @Test
    public void validate_should_throwException_when_accountFromNotFound() throws Exception {
        final Account accountTo = new Account("102");
        final Transfer transfer = new Transfer("101", accountTo.getAccountId(), new BigDecimal("2.00"));

        try {
            transferValidator.validate(null, new Account("102"), transfer);
            fail("Account with 101 should not be found");
        } catch (AccountNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Account 101 not found.");
        }
    }

    @Test
    public void validate_should_throwException_when_accountToNotFound() throws Exception {
        final Account accountFrom = new Account("101");
        final Transfer transfer = new Transfer("101", "Id-5342", new BigDecimal("2.00"));

        try {
            transferValidator.validate(accountFrom, null, transfer);
            fail("Account with Id-5342 should not be found");
        } catch (AccountNotFoundException ace) {
            assertThat(ace.getMessage()).isEqualTo("Account Id-5342 not found.");
        }
    }

    @Test
    public void validate_should_throwException_when_NotEnoughFunds() throws Exception {
        final Account accountFrom = new Account("101");
        final Account accountTo = new Account("102");
        final Transfer transfer = new Transfer("101", "102", new BigDecimal("2.00"));

        try {
            transferValidator.validate(accountFrom, accountTo, transfer);
            fail("Not enough funds");
        } catch (NotEnoughFundsException nbe) {
            assertThat(nbe.getMessage()).isEqualTo("Not enough funds on account 101 balance=0");
        }
    }

    @Test
    public void validate_should_throwException_when_transferBetweenSameAccount() throws Exception {
        final Account accountFrom = new Account("101", new BigDecimal("20.00"));
        final Account accountTo = new Account("101");
        final Transfer transfer = new Transfer("101", "101", new BigDecimal("2.00"));

        try {
            transferValidator.validate(accountFrom, accountTo, transfer);
            fail("Same account transfer");
        } catch (TransferBetweenSameAccountException sae) {
            assertThat(sae.getMessage()).isEqualTo("Transfer to self not permitted.");
        }
    }

    @Test
    public void validate_should_allowValidTransferBetweenAccounts() throws Exception {
        final Account accountFrom = new Account("101", new BigDecimal("20.00"));
        final Account accountTo = new Account("102");
        final Transfer transfer = new Transfer("101", "102", new BigDecimal("2.00"));

        transferValidator.validate(accountFrom, accountTo, transfer);
    }

}