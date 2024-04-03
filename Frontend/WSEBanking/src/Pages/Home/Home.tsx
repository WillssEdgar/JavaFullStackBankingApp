import onlinebanking from "../../assets/onlinebanking.jpg";
import twoBankers from "../../assets/twocomputers.jpg";
import webBanking from "../../assets/webbanking.jpg";
import "./Home.css";

function Home() {
  return (
    <div className="homeContainer rounded-3">
      <div
        className="container-fluid p-4 d-flex justify-content-center rounded-3 "
        style={{ marginTop: "10%" }}
      >
        <div className="row ">
          <div className="col ">
            <div
              id="carouselExampleFade"
              className="carousel slide carousel-fade rounded-3"
              style={{
                maxHeight: "500px",
                maxWidth: "800px",
                boxShadow: "10px 10px 20px black",
              }}
            >
              <div className="carousel-inner ">
                <div className="carousel-item active ">
                  <img
                    src={onlinebanking}
                    className="img-fluid d-block w-100 rounded-3"
                    style={{ maxHeight: "500px" }}
                    alt="Online Banking"
                  />
                </div>
                <div className="carousel-item rounded-3">
                  <img
                    src={twoBankers}
                    className="img-fluid d-block w-100 rounded-3"
                    style={{ maxHeight: "500px" }}
                    alt="Two Bankers"
                  />
                </div>
                <div className="carousel-item rounded-3">
                  <img
                    src={webBanking}
                    className="img-fluid d-block w-100 rounded-3"
                    style={{ maxHeight: "500px" }}
                    alt="Web Banking"
                  />
                </div>
              </div>
              <button
                className="carousel-control-prev rounded-3"
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
                className="carousel-control-next rounded-3"
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

      <div className="container mt-5 mb-5 ">
        <div className="row justify-content-center text-center">
          <div className="col-md-6 mb-4">
            <h2 className="text-dark">What can we help you with?</h2>
          </div>
        </div>
        <div className="row justify-content-center">
          <div
            className="col-md-5 m-3 p-4 rounded-3 blur bg-dark bg-opacity-25"
            style={{ boxShadow: "10px 10px 20px black" }}
          >
            <h3>Explore Our Services</h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed id
              nisi vel ipsum dignissim lobortis.
            </p>
            <button className="btn btn-light">Learn More</button>
          </div>
          <div
            className="col-md-5 m-3 p-4 rounded-3 blur bg-dark bg-opacity-25"
            style={{ boxShadow: "10px 10px 20px black" }}
          >
            <h3>Get Started with Online Banking</h3>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed id
              nisi vel ipsum dignissim lobortis.
            </p>
            <button className="btn btn-light">Sign Up Now</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
