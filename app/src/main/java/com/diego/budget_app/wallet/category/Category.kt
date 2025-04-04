package com.diego.budget_app.wallet.category

sealed class Category {
    abstract var name: String
    abstract var sumIterations: Float
}
// Подклассы для расходов
sealed class ExpenseCategory(
    override var name: String,
    override var sumIterations: Float = 0f
) : Category() {
    data object Rent : ExpenseCategory("Аренда")
    data object Utilities : ExpenseCategory("Коммунальные услуги")

    data class CustomExpense(
        override var name: String,
        override var sumIterations: Float = 0f,
    ) : ExpenseCategory(name)
}

//Подклассы для доходов
sealed class IncomeCategory(
    override var name: String,
    override var sumIterations: Float = 0f
) : Category() {
    data object Salary : IncomeCategory("Зарплата")
    data object Freelance : IncomeCategory("Фриланс")

    data class CustomIncome(
        override var name: String,
        override var sumIterations: Float = 0f
    ) : IncomeCategory(name, sumIterations)
}