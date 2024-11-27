import {createContext, PropsWithChildren, useContext} from "react";

export type SessionData = {
  accessToken: string | null,
  setAccessToken: (value: string | null) => void,
}

const SessionContext = createContext<SessionData | undefined>(undefined);

const accessTokenKey: string = "access-token";

export function getCachedAccessToken(): string | null {
  return localStorage.getItem(accessTokenKey);
}

export function SessionProvider(props: PropsWithChildren<SessionData>) {
  function setToken(value: string | null) {
    if (value !== null)
      localStorage.setItem(accessTokenKey, value);
    else
      localStorage.removeItem(accessTokenKey);

    props.setAccessToken(value);
  }

  const sessionData: SessionData = {
    accessToken: props.accessToken,
    setAccessToken: setToken
  };

  return (
    <SessionContext.Provider value={sessionData}>
      {props.children}
    </SessionContext.Provider>
  )
}

export default function useSession(): SessionData {
  const context = useContext(SessionContext);
  if (context === undefined)
    throw new Error("useSession must be used within a SessionProvider");

  return context;
}
