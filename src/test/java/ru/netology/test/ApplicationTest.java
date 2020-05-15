package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    int amount = 200;

    @Test
    void shouldTransferFromSecondToFirst() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.firstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        transferPage.transferCard(cardInfo);
        int balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardWhenTransferDone(balanceOfFirstCardBefore, amount);
        int balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardWhenTransferDone(balanceOfSecondCardBefore, amount);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.verify(verificationCode);
        int balanceOfFirstCardBefore = DashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardBefore = DashboardPage.getCurrentBalanceOfSecondCard();
        val rechargingPage = dashboardPage.secondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        rechargingPage.transferCard(cardInfo);
        int balanceAfterTransferFirstCard = DataHelper.balanceOfSecondCardWhenTransferDone(balanceOfSecondCardBefore, amount);
        int balanceAfterTransferSecondCard = DataHelper.balanceOfFirstCardWhenTransferDone(balanceOfFirstCardBefore, amount);
        int balanceOfFirstCardAfter = DashboardPage.getCurrentBalanceOfSecondCard();
        int balanceOfSecondCardAfter = DashboardPage.getCurrentBalanceOfFirstCard();
        assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
        assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
    }
}