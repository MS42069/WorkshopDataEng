import { axiosInstance } from "../config/axios.config";
import { ConstraintReqDTO } from "./constraints";
export type CoursePreferenceProfile = {
  courseId: string;
  parallelCourseId: string | undefined;
  predecessorCourseId: string | undefined;
  preferredRoomId: string | undefined;
  alternateRoomId: string | undefined;
  weeklySwitchOfRooms: boolean;
  preferredBlockSize: number;
};

export type CourseConstraintsDTO = {
  parallelWantedCourse: CourseRelatedCourseConstraint;
  predecessorCourseWanted: CourseRelatedCourseConstraint;
  preferredRoom: RoomRelatedCourseConstraint;
  alternateRoomId: RoomRelatedCourseConstraint;
  weeklyRoomSwitch: TypedCourseConstraint<boolean>;
  preferredBlockSize: TypedCourseConstraint<number>;
};

export type TypedCourseConstraint<T> = {
  courseId: string;
  value: T;
};

export type RoomRelatedCourseConstraint = {
  courseId: string;
  roomId: string | null;
};

export type CourseRelatedCourseConstraint = {
  courseId: string;
  relatedCourseId: string | null;
};

export const saveCourseConstraintProfile = async (
  profile: CoursePreferenceProfile
) => {
  let courseID = profile.courseId;
  let constraintReqDTO: CourseConstraintsDTO = {
    parallelWantedCourse: {
      courseId: courseID,
      relatedCourseId: profile.parallelCourseId ?? null,
    },
    predecessorCourseWanted: {
      courseId: courseID,
      relatedCourseId: profile.predecessorCourseId ?? null,
    },
    preferredRoom: {
      courseId: courseID,
      roomId: profile.preferredRoomId ?? null,
    },
    alternateRoomId: {
      courseId: courseID,
      roomId: profile.alternateRoomId ?? null,
    },
    weeklyRoomSwitch: {
      courseId: courseID,
      value: profile.weeklySwitchOfRooms,
    },
    preferredBlockSize: {
      courseId: courseID,
      value: profile.preferredBlockSize == 0 ? 1 : profile.preferredBlockSize,
    },
  };

  await axiosInstance.post<CourseConstraintsDTO>(
    `/course-preference-profile/save`,
    constraintReqDTO,
    { cache: false }
  );
};
