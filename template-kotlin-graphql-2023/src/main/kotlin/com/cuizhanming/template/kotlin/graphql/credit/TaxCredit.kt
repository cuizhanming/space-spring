package com.cuizhanming.template.kotlin.graphql.credit

import com.cuizhanming.template.kotlin.codegen.types.TaxCredit
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service


@Controller
class TaxCreditController(
    private val taxCreditService: TaxCreditService
) {
    @QueryMapping
    fun availableCredits(@Argument year: Int): List<TaxCredit> {
        return taxCreditService.getAvailableCredits(year)
    }
}

@Service
class TaxCreditService {

    fun getAvailableCredits(year: Int): List<TaxCredit> {
        return listOf(
            TaxCredit(
                id = "1",
                name = "Tax Credit 1",
                year = 2021,
                amount = 100.0,
                type = "Type 1",
                issuedAt = "2021-01-01",
                createdAt = "2021-01-01",
                updatedAt = "2021-01-01"
            ),
            TaxCredit(
                id = "2",
                name = "Tax Credit 2",
                year = 2021,
                amount = 200.0,
                type = "Type 2",
                issuedAt = "2021-01-01",
                createdAt = "2021-01-01",
                updatedAt = "2021-01-01"
            ),
            TaxCredit(
                id = "3",
                name = "Tax Credit 3",
                year = 2021,
                amount = 300.0,
                type = "Type 3",
                issuedAt = "2021-01-01",
                createdAt = "2021-01-01",
                updatedAt = "2021-01-01"
            )
        )
    }
}