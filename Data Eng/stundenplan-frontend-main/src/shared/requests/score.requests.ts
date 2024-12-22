import { resourceAxiosInstance } from "../../config/axios.config";
import { ScoreAPI } from "../../models/score";
import { createTimetablePath } from "../path.util";

export const fetchScore = () =>
  resourceAxiosInstance.get<ScoreAPI>("/score", {
    id: createTimetablePath("score"),
    cache: false,
  });
