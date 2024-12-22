import Context from "react-redux/es/components/Context";
import {
  AppAvailabilityDTO,
  getAppAvailability,
} from "../../models/app-availability";
import { useEffect, useState } from "react";
import React from "react";

export const CustomContextVal = React.createContext<CustomContext>({
  unlock: null,
});

export type CustomContext = {
  unlock: AppAvailabilityDTO | null;
};

type Props = {
  children: React.ReactNode;
};

export const dayFormatWithHour = "DD.MM.YYYY HH:mm";

export function ContextProvider(props: Props) {
  const [context, setContext] = useState<CustomContext>({ unlock: null });
  const [isLoading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    if (isLoading) {
      getAppAvailability().then((value) => {
        if (value) {
          let newContext = context;
          newContext.unlock = value;
          setContext(newContext);
          setLoading(false);
        }
      });
    }
  }, [context, isLoading]);

  return (
    <CustomContextVal.Provider value={context}>
      {props.children}
    </CustomContextVal.Provider>
  );
}

export function currentlyUnlocked(
  unlockDate: Date | null,
  lockDate: Date | null
): boolean {
  if (unlockDate == null || lockDate == null) {
    return false;
  }
  const now = new Date();
  const unlock = new Date(unlockDate);
  const lock = new Date(lockDate);

  return now > unlock && now < lock;
}
