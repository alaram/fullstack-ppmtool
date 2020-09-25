import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./types";

// Create a project
export const createProject = (project, history) => async (dispatch) => {
  try {
    await axios.post("/api/project", project);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

// Retrieve all projects
export const getProjects = () => async (dispatch) => {
  const res = await axios.get("/api/project/all");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};

// Retrieve project by ID
export const getProject = (id, history) => async (dispatch) => {
  try {
    const res = await axios.get(`/api/project/${id}`);
    dispatch({
      type: GET_PROJECT,
      payload: res.data,
    });
  } catch (err) {
    history.push("/dashboard");
  }
};

// Delete project
export const deleteProject = (id, history) => async (dispatch) => {
  if (
    window.confirm(
      "Are you sure? This will delete all the project informaton and its related data"
    )
  ) {
    await axios.delete(`/api/project/${id}`);
    dispatch({
      type: DELETE_PROJECT,
      payload: id,
    });
  }
};
