from datetime import datetime

class Transaction:
    def __init__(self, transaction_type, amount, date):
        self.transaction_type = transaction_type
        self.amount = amount
        self.date = date


class BankAccount:
    def __init__(self, account_number, account_name, balance=0):
        self._account_number = account_number
        self._account_name = account_name
        self._balance = balance
        self._transactions = []

    def deposit(self, amount):
        if amount > 0:
            self._balance += amount
            self._transactions.append(Transaction("Deposit", amount, datetime.now()))
            print(f"Deposited {amount} into account {self._account_number}")
        else:
            print("Invalid deposit amount")

    def withdraw(self, amount):
        if amount > 0 and amount <= self._balance:
            self._balance -= amount
            self._transactions.append(Transaction("Withdrawal", amount, datetime.now()))
            print(f"Withdrew {amount} from account {self._account_number}")
        else:
            print("Invalid withdrawal amount or insufficient balance")

    def display_account_details(self):
        print(f"Account Number: {self._account_number}")
        print(f"Account Holder Name: {self._account_name}")
        print(f"Balance: {self._balance}")

    def display_transactions(self):
        print("Transaction History:")
        for transaction in self._transactions:
            print(f"{transaction.transaction_type}: {transaction.amount}, Date: {transaction.date}")


class SavingsAccount(BankAccount):
    def __init__(self, account_number, account_name, balance=0, min_balance=0):
        super().__init__(account_number, account_name, balance)
        self._min_balance = min_balance

    def withdraw(self, amount):
        if self._balance - amount >= self._min_balance:
            super().withdraw(amount)
        else:
            print("Withdrawal failed: Insufficient balance or below minimum balance")

    def display_account_details(self):
        super().display_account_details()
        print(f"Minimum Balance: {self._min_balance}")

class CheckingAccount(BankAccount):
    def __init__(self, account_number, account_name, balance=0, overdraft_limit=0):
        super().__init__(account_number, account_name, balance)
        self._overdraft_limit = overdraft_limit

    def withdraw(self, amount):
        if self._balance - amount >= -self._overdraft_limit:
            super().withdraw(amount)
        else:
            print("Withdrawal failed: Insufficient funds and overdraft limit exceeded")

    def display_account_details(self):
        super().display_account_details()
        print(f"Overdraft Limit: {self._overdraft_limit}")

class RegisteredSavingsAccount(SavingsAccount):
    def __init__(self, account_number, account_name, balance=0, min_balance=0, registered_type=''):
        super().__init__(account_number, account_name, balance, min_balance)
        self._registered_type = registered_type

    def display_account_details(self):
        super().display_account_details()
        print(f"Registered Type: {self._registered_type}")


class Loan:
    def __init__(self, account_number, loan_amount, interest_rate):
        self._account_number = account_number
        self._loan_amount = loan_amount
        self._interest_rate = interest_rate
        self._remaining_amount = loan_amount

    def make_payment(self, amount):
        if amount > 0:
            interest_amount = self._remaining_amount * (self._interest_rate / 100)
            total_payment = amount + interest_amount
            if total_payment <= self._remaining_amount:
                self._remaining_amount -= total_payment
                print(f"Payment of {amount} made for loan {self._account_number}")
                return True
            else:
                print("Payment exceeds remaining loan amount")
        else:
            print("Invalid payment amount")
        return False

    def display_loan_details(self):
        print(f"Loan Account Number: {self._account_number}")
        print(f"Loan Amount: {self._loan_amount}")
        print(f"Remaining Amount: {self._remaining_amount}")
        print(f"Interest Rate: {self._interest_rate}%")

def test_bank_accounts():
    # Create instances of BankAccount, SavingsAccount, and CheckingAccount
    bank_acct = BankAccount(1001, "Elon Musk")
    savings_acct = SavingsAccount(2001, "Bill Gates", min_balance=100)
    checking_acct = CheckingAccount(3001, "Steve Jobs", overdraft_limit=200)
    registered_savings_acct = RegisteredSavingsAccount(4001, "Jeff Bezos", min_balance=500, registered_type="TFSA")

    # Test transactions
    bank_acct.deposit(1000)
    bank_acct.withdraw(500)
    bank_acct.display_transactions()
    print()

    savings_acct.deposit(2000)
    savings_acct.withdraw(150)
    savings_acct.display_transactions()
    print()

    checking_acct.deposit(3000)
    checking_acct.withdraw(3500)  
    checking_acct.display_account_details()

    registered_savings_acct.deposit(3000)
    registered_savings_acct.withdraw(400)  
    registered_savings_acct.display_transactions()
    print()

    # Test loans
    loan = Loan(4001, 10000, 5)
    loan.make_payment(1500)
    loan.display_loan_details()


if __name__ == "__main__":
    test_bank_accounts()
