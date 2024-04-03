import axios from "axios";
import { useEffect, useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import LineChart from "../../Components/Charts/LineChart";
interface Account {
  id: string;
  accountName: string;
  accountNumber: string;
  balance: number;
}

interface Transaction {
  accountNumber: string;
  amount: number;
  transactionDate: Array<number>;
  transactionType: string;
}

function Account() {
  const withdrawalAmountRef = useRef<HTMLInputElement>(null);
  const depositAmountRef = useRef<HTMLInputElement>(null);

  const [showWithdrawalMenu, setShowWithdrawalMenu] = useState(false);
  const [showDepositMenu, setShowDepositMenu] = useState(false);

  const [account, setAccount] = useState<Account | null>(null);
  const [transactions, setTransaction] = useState<Transaction[]>([]);

  const location = useLocation();
  const user_Id = parseInt(location.state.user_Id);
  const token = location.state.token;
  const accountId = location.state.id;
  const accountNumber = location.state.accountNumber;

  const [accountBalance, setAccountBalance] = useState(0);

  const [withdrawalErrors, setWithdrawalErrors] = useState<{
    [key: string]: string;
  }>({});
  const [depositErrors, setDepositErrors] = useState<{
    [key: string]: string;
  }>({});

  console.log("Location from Account", location.state);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/accounts/account`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            params: {
              accountNumber: accountNumber,
              userId: user_Id,
            },
          }
        );
        setAccountBalance(response.data.balance);
        setAccount(response.data);
      } catch (error) {
        console.error("Error fetching accounts: ", error);
      }
    };
    if (token && user_Id) {
      fetchAccounts();
    }
    const fetchTransactions = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/transactions/getTransactions`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            params: {
              accountId: accountId,
            },
          }
        );
        console.log(response.data);
        setTransaction(response.data);
      } catch (error) {
        console.error("Error fetching accounts: ", error);
      }
    };
    if (token && user_Id) {
      fetchTransactions();
    }
  }, [token, user_Id]);

  const handWithdrawalClick = () => {
    setShowWithdrawalMenu((prev) => !prev);
  };

  const handleWithdrawalConfirm = async () => {
    const withdrawalAmount = withdrawalAmountRef.current?.value?.trim();
    const errors: { [key: string]: string } = {};

    if (!withdrawalAmount) {
      errors.withdrawalAmount = "A positive Withdrawal amount is required";
    }
    if (Object.keys(errors).length > 0) {
      setWithdrawalErrors(errors);
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/accounts/transactions/withdrawal",
        {
          accountId: accountId,
          amount: withdrawalAmount,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setShowWithdrawalMenu(false);
      window.location.reload();
    } catch (error) {
      errors.withdrawalAmount = "Insufficient funds. Please try again.";
      if (Object.keys(errors).length > 0) {
        setWithdrawalErrors(errors);
        return;
      }
    }
  };

  const handleDepositClick = () => {
    setShowDepositMenu((prev) => !prev);
  };
  const handleDepositConfirm = async () => {
    const depositAmount = depositAmountRef.current?.value?.trim();
    const errors: { [key: string]: string } = {};

    if (!depositAmount) {
      errors.depositAmount = "A positive Deposit amount is required";
    }
    if (Object.keys(errors).length > 0) {
      setDepositErrors(errors);
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/accounts/transactions/deposit",
        {
          accountId: accountId,
          amount: depositAmount,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setAccountBalance(
        (prevBalance) => prevBalance + parseFloat(depositAmount ?? "")
      );

      setShowDepositMenu(false);
      window.location.reload();
    } catch (error) {
      console.error("Error depositing funds: ", error);
      // Handle error (e.g., display error message to the user)
    }
  };

  return (
    <div className="container" style={{ marginTop: "10%" }}>
      <div className="row justify-content-center">
        <div
          className="col-md-12 m-4 p-3 rounded-3"
          style={{ boxShadow: "10px 10px 20px black" }}
        >
          {<LineChart data={{ transactions }} />}
        </div>
      </div>
      <div className="row justify-content-center">
        {account ? (
          <div className="col-md-6 ">
            <div
              className="card border-0 p-3 bg-transparent mt-5"
              style={{ boxShadow: "10px 10px 20px black" }}
            >
              <h3>Account Number: {account.accountNumber}</h3>
              <h3>Balance: ${accountBalance.toFixed(2)}</h3>

              <h3 className="mb-3">Actions</h3>
              <button
                type="button"
                className="btn btn-primary btn-lg mb-3"
                onClick={handWithdrawalClick}
              >
                Withdrawal
              </button>
              {showWithdrawalMenu && (
                <div className="mb-3">
                  <input
                    type="number"
                    ref={withdrawalAmountRef}
                    placeholder="Enter Withdrawal Amount"
                    className="form-control mb-2 border-dark"
                  />
                  {withdrawalErrors.withdrawalAmount && (
                    <p className="text-danger">
                      {withdrawalErrors.withdrawalAmount}
                    </p>
                  )}
                  <button
                    type="button"
                    className="btn btn-primary"
                    onClick={handleWithdrawalConfirm}
                  >
                    Confirm Withdrawal
                  </button>
                </div>
              )}

              <button
                type="button"
                className="btn btn-primary btn-lg mb-3"
                onClick={handleDepositClick}
              >
                Deposit
              </button>
              {showDepositMenu && (
                <div className="mb-3">
                  <input
                    type="number"
                    ref={depositAmountRef}
                    placeholder="Enter Deposit Amount"
                    className="form-control border-dark mb-2"
                  />
                  {depositErrors.depositAmount && (
                    <p className="text-danger">{depositErrors.depositAmount}</p>
                  )}
                  <button
                    type="button"
                    className="btn btn-primary"
                    onClick={handleDepositConfirm}
                  >
                    Confirm Deposit
                  </button>
                </div>
              )}
            </div>
          </div>
        ) : (
          <h3>Loading Content...</h3>
        )}
        <div
          className="col-md-6 mt-5 mb-5 p-3 rounded-3"
          style={{ boxShadow: "10px 10px 20px black" }}
        >
          <p className="h1">Transaction History</p>
          <div
            className="p-3 bg-transparent"
            style={{ overflowY: "auto", maxHeight: "400px" }}
          >
            {/* Adjust maxHeight to your preference */}
            {transactions.length > 0 ? (
              transactions
                .slice()
                .reverse()
                .map((transaction, index) => (
                  <div
                    key={index}
                    className="card mb-3 bg-primary border-0 w-100 text-light"
                    style={{ boxShadow: "5px 5px 10px black" }}
                  >
                    <div className="card-body p-2">
                      <h5 className="card-title">
                        Transaction Type: {transaction.transactionType}
                      </h5>
                      <p className="card-text">
                        Amount: ${transaction.amount.toFixed(2)} <br />
                        Transaction Date: {transaction.transactionDate[0]}-
                        {transaction.transactionDate[1]}-
                        {transaction.transactionDate[2]}
                      </p>
                    </div>
                  </div>
                ))
            ) : (
              <h4> No transactions found.</h4>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Account;
