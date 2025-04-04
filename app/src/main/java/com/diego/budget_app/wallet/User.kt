package com.diego.budget_app.wallet

import com.diego.budget_app.wallet.category.Category
import com.diego.budget_app.wallet.category.ExpenseCategory
import com.diego.budget_app.wallet.category.IncomeCategory


class User(
    private val id: Long,
    private var name: String,
    private val startMainActivity: String,
    private val startBudget: Float,
    private var incomeDefault: ArrayList<IncomeCategory> = arrayListOf(
        IncomeCategory.Salary,
        IncomeCategory.Freelance
    ),
    private var expenseDefault: ArrayList<ExpenseCategory> = arrayListOf(
        ExpenseCategory.Rent,
        ExpenseCategory.Utilities
    )
) {
    var budget: Float = 0f
        private set

    var arrayCustomCategory: MutableMap<String, Category> = mutableMapOf()
        private set

    init {
        budget = if (startBudget > 0) startBudget else 0f
    }

    fun getCustomCategory(name: String) : Category {
        val cat = arrayCustomCategory[name] ?: throw IllegalArgumentException("Category $name is not found")

        return when(cat){
            is IncomeCategory -> cat
            is ExpenseCategory -> cat
            else -> throw ClassCastException("Unknown category type: ${cat::class.java}")
        }
    }

    fun getSumIncome() : Float {
        var sum: Float = 0f
        for (i in incomeDefault){
            sum += i.sumIterations
        }

        for (i in arrayCustomCategory){
            if (i is IncomeCategory) {
                sum += i.value.sumIterations
            }
        }
        return sum
    }

    fun addIncome(name: String){
        arrayCustomCategory[name] = IncomeCategory.CustomIncome(name)
    }

    fun addExpense(name: String){
        arrayCustomCategory[name] = ExpenseCategory.CustomExpense(name)
    }

    fun topUpBalance(categoryIncome: Category, value: Float){
        budget += value
        categoryIncome.sumIterations += value
    }

    fun takeFromBalance(categoryExpense: Category, value: Float){
        budget -= value
        categoryExpense.sumIterations += value
    }

    override fun toString(): String {
        return buildString {
            append("id: $id \n")
            append("name: $name \n")
            append("startMainActivity: $startMainActivity \n")
            append("startBudget: $startBudget \n")
            append("budget: $budget\n")
            append("Доход\n")
            for (i in incomeDefault){
                append("${i.name} = ${i.sumIterations}\n")
            }
            append("Расход\n")
            for (i in expenseDefault){
                append("${i.name} = ${i.sumIterations}\n")
            }
            append("Кастомные категории\n")
            for (i in arrayCustomCategory){
                append("${i.key} = ${i.value.sumIterations}\n")
            }
        }
    }
}