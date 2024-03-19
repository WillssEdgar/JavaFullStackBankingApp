import { useState } from "react";

function Account() {
  const accountId = localStorage.getItem("id");
  const accountNumber = localStorage.getItem("accountNumber");
  const accountName = localStorage.getItem("accountName");

  const [withdrawalAmount, setWithdrawalAmount] = useState("");
  const [showWithdrawalMenu, setShowWithdrawalMenu] = useState(false);

  const handWithdrawalClick = () => {
    setShowWithdrawalMenu(true);
  };

  const handleWithdrawalConfirm = () => {
    console.log("Withdrawal amount:", withdrawalAmount);
    setShowWithdrawalMenu(false);
    setWithdrawalAmount("");
  };

  return (
    <div>
      <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row justify-content-center text-center">
          <h3>Account Number: {accountNumber}</h3>
          <div className="col bg-light-subtle p-5 rounded-4 text-center">
            <h3>Actions: </h3>
            <div className="d-grid gap-2 d-md-block">
              <button
                type="button"
                className="btn btn-primary btn-lg m-3"
                onClick={handWithdrawalClick}
              >
                Withdrawal
              </button>
              {showWithdrawalMenu && (
                <div className="m-3">
                  <input
                    type="number"
                    value={withdrawalAmount}
                    onChange={(e) => setWithdrawalAmount(e.target.value)}
                    placeholder="Enter Withdrawal Amount"
                    className="form-control mb-2"
                  />
                  <button
                    type="button"
                    className="btn btn-primary btn-lg"
                    onClick={handleWithdrawalConfirm}
                  >
                    Confirm Withdrawal
                  </button>
                </div>
              )}
              <button type="button" className="btn btn-primary btn-lg m-3">
                Deposit
              </button>
              <button type="button" className="btn btn-primary btn-lg m-3">
                Add Account
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Account;
