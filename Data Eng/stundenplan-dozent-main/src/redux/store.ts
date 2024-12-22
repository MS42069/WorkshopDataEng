import { configureStore } from "@reduxjs/toolkit";
import sideNavReducer from "./side-nav.reducer";
import authReducer from "./auth.reducer";
import notificationReducer from "./notification.reducer";
import csrfReducer from "./csrf.reducer";
import errorReducer from "./error.reducer";
import spinnerReducer from "./spinner.reducer";

export const store = configureStore({
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
  reducer: {
    sideNavReducer,
    authReducer,
    notificationReducer,
    csrfReducer,
    errorReducer,
    spinnerReducer,
  },
});

// https://redux.js.org/tutorials/typescript-quick-start
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;

export type StoreState = typeof store;
