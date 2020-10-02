import axios from "axios";
import setJWTToken from "../securityUtils/setJWTToken";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import jwt_decode from "jwt-decode";

/**
 *
 * @param {*} newUser
 * @param {*} history
 */
export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");

    // This clean any error that get hang in the state, i.e.
    // cleaning the payload
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

/**
 *
 * @param {*} LoginRequest
 */
export const login = (LoginRequest) => async (dispatch) => {
  try {
    // post => Login Request
    const res = await axios.post("/api/users/login", LoginRequest);

    // Extract token from res.data, i.e. destructoring
    const { token } = res.data;

    // Store the token in the localStorage
    localStorage.setItem("jwtToken", token);

    // Set the token in the Header ***
    setJWTToken(token);

    // Decode token on REACT
    //const decoded = jwt_decode(token, { header: true });
    const decoded = jwt_decode(token);

    // Dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
