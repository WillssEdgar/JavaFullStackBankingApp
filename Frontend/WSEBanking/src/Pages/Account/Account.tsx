function Account() {
  return (
    <div>
      <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row justify-content-center">
          <div className="col bg-light-subtle p-5 rounded-4 text-center">
            <h3>Actions: </h3>
            <div className="d-grid gap-2 d-md-block">
              <button type="button" className="btn btn-primary btn-lg m-3">
                Withdrawal
              </button>
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
