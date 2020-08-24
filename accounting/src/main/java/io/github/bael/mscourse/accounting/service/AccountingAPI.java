package io.github.bael.mscourse.accounting.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Апи для работы со счетом клиента
 * наивная реализация без системы счетов.
 */
public interface AccountingAPI {
    /**
     * Создаем начисления по счету
     * Для упрощения, считаем что кодом счета является код клиента
     * @param sum - сумма заказа
     * @param orderDate - дата заказа
     * @param customerCode - код заказа
     * @param orderCode - уид счета (считаем что это уид клиента)
     */
    void chargeOrder(BigDecimal sum, LocalDate orderDate, String customerCode, String orderCode);

    /**
     * Сторнируем начисления по заказу (полностью или частично)
     * @param sum сумма
     * @param reversalDate  дата сторно
     * @param orderId - код заказа
     * @param accountId - код счета
     */
    void dischargeOrder(BigDecimal sum, LocalDate reversalDate, String customerCode, String orderCode);

    /**
     * Регистриуем платеж
     * @param orderId код заказа
     * @param accountId код счета
     * @param paymentId
     * @param sum сумма
     * @param paymentDate дата платежа
     */
    void registerPayment(String paymentId, BigDecimal sum, LocalDate paymentDate, String customerCode, String orderCode);

    /**
     * Возврат денег клиенту
     * Вернуть можно только то что излишне оплачено в разрезе заказа. Т.е. не более положительного баланса по заказу.
     * @param sum - сумма
     * @param operationDate дата возврата
     * @param orderId код заказа
     * @param accountId - счет
     *
     */
    void moneyBack(BigDecimal sum, LocalDate operationDate, String customerCode, String orderCode);

    /**
     * Баланс счета
     * @param accountId - код счета
     * @param queryDate - на какую дату смотрим баланс
     * @return сумму (оплат - начислений)
     */
    BigDecimal balance(String customerCode, LocalDate queryDate);

    /**
     * Баланс счета по заказу
     * @param accountId - код счета
     * @param orderId - код заказа
     * @param queryDate - на какую дату смотрим баланс
     * @return сумму (оплат - начислений)
     */
    BigDecimal balance(String customerCode, String orderCode, LocalDate queryDate);

}
