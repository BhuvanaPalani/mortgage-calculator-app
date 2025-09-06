package se.linerotech.module201.project2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.math.pow

class CalculatorActivity : AppCompatActivity() {

    companion object {
        private const val MONTHS_IN_YEAR = 12
        private const val PERCENT_DIVISOR = 100.0
        private const val ZERO_RATE = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etLoanAmount = findViewById<EditText>(R.id.etLoanAmount)
        val etInterestRate = findViewById<EditText>(R.id.etInterestRate)
        val etYears = findViewById<EditText>(R.id.etYears)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)

        btnCalculate.setOnClickListener {
            val loanText = etLoanAmount.text.toString()
            val rateText = etInterestRate.text.toString()
            val yearsText = etYears.text.toString()

            if (loanText.isEmpty() || rateText.isEmpty() || yearsText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val principal = loanText.toDouble()
            val annualRatePercent = rateText.toDouble()
            val years = yearsText.toInt()

            val monthlyRate = (annualRatePercent / PERCENT_DIVISOR) / MONTHS_IN_YEAR
            val totalMonths = years * MONTHS_IN_YEAR

            val monthlyPayment = if (monthlyRate == ZERO_RATE) {
                principal / totalMonths
            } else {
                val factor = (1 + monthlyRate).pow(totalMonths.toDouble())
                principal * monthlyRate * factor / (factor - 1)
            }

            tvResult.text = String.format(Locale.US, "%.2f kr / month", monthlyPayment)
        }
    }
}
