import onlinebanking from "../../assets/onlinebanking.jpg";
import twoBankers from "../../assets/twocomputers.jpg";
import webBanking from "../../assets/webbanking.jpg";

function Home() {
  return (
    <div>
      <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row">
          <div className="col">
            <div
              id="carouselExampleFade"
              className="carousel slide carousel-fade"
            >
              <div className="carousel-inner">
                <div className="carousel-item active">
                  <img
                    src={onlinebanking}
                    className="d-block w-100 rounded-4"
                    alt="..."
                  />
                </div>
                <div className="carousel-item">
                  <img
                    src={twoBankers}
                    className="d-block w-100 rounded-4"
                    alt="..."
                  />
                </div>
                <div className="carousel-item">
                  <img
                    src={webBanking}
                    className="d-block w-100 rounded-4"
                    alt="..."
                  />
                </div>
              </div>
              <button
                className="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExampleFade"
                data-bs-slide="prev"
              >
                <span
                  className="carousel-control-prev-icon"
                  aria-hidden="true"
                ></span>
                <span className="visually-hidden">Previous</span>
              </button>
              <button
                className="carousel-control-next"
                type="button"
                data-bs-target="#carouselExampleFade"
                data-bs-slide="next"
              >
                <span
                  className="carousel-control-next-icon"
                  aria-hidden="true"
                ></span>
                <span className="visually-hidden">Next</span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="container d-flex align-items-center justify-content-center vh-30  mb-5">
        <div className="row justify-content-evenly ">
          <h1>What can we help you with?</h1>
          <div className="col bg-primary"> hello world</div>

          <div className="col bg-success"> hello world</div>
        </div>
      </div>
    </div>
  );
}

export default Home;
