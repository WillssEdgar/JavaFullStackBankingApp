import { Component } from "react";
import { request, setAuthHeader } from "../../Helpers/axios_helper";
import { AxiosResponse } from "axios";

interface AuthContentState {
  data: string[];
}

export default class AuthContent extends Component<{}, AuthContentState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      data: [],
    };
  }

  componentDidMount() {
    request("GET", "/messages", {})
      .then((response: AxiosResponse<any, any>) => {
        this.setState({ data: response.data });
      })
      .catch((error: any) => {
        if (error.response?.status === 401) {
          setAuthHeader("");
        } else {
          this.setState({ data: [error.response.code] });
        }
      });
  }

  render() {
    return (
      <div className="row justify-content-md-center">
        <div className="col-4">
          <div className="card" style={{ width: "18rem" }}>
            <div className="card-body">
              <h5 className="card-title">Backend response</h5>
              <p className="card-text">Content:</p>
              <ul>
                {this.state.data &&
                  this.state.data.map((line, index) => (
                    <li key={index}>{line}</li>
                  ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
