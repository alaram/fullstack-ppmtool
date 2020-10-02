import React from "react";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

// Required imports for CSS
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";

// *******************************************
// Routes concerning the application

// Private routes
import Header from "./components/Layout/Header";
import Dashboard from "./components/Dashboard";
import AddProject from "./components/Project/AddProject";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTask/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTask/UpdateProjectTask";
import SecureRoute from "./securityUtils/SecureRoute";

// Public routes
import Landing from "./components/Layout/Landing";
import Login from "./components/UserManagement/Login";
import Register from "./components/UserManagement/Register";

// Imports for extra functionality, i.e. JWT token, store, etc
import store from "./store";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";

import { logout } from "./actions/securityActions";

// *******************************************
// This code has the following functionality
// The "JWT" token is setted in the Authorization
// header once the user login, but since all
// components have a meeting place @ App.js then
// get we have to set the token yet again in order
// to interact with the components once again.
const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decodedToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decodedToken,
  });

  const currentTime = Date.now() / 1000;
  if (decodedToken.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}
// *******************************************

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          {
            // Public routes
          }
          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          {
            // Private routes
          }
          <Switch>
            <SecureRoute exact path="/dashboard" component={Dashboard} />
            <SecureRoute exact path="/addProject" component={AddProject} />
            <SecureRoute
              exact
              path="/updateProject/:id"
              component={UpdateProject}
            />
            <SecureRoute
              exact
              path="/projectBoard/:id"
              component={ProjectBoard}
            />
            <SecureRoute
              exact
              path="/addProjectTask/:id"
              component={AddProjectTask}
            />
            <SecureRoute
              exact
              path="/updateProjectTask/:backlog_id/:pt_id"
              component={UpdateProjectTask}
            />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
