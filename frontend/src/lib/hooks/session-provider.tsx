import {createContext, PropsWithChildren} from "react";

type SessionProviderProps = {
  accessToken: string | null,
  setAccessToken: (value: string | null) => void,
}

const SessionContext = createContext<SessionProviderProps | null>(null);

const accessTokenKey: string = "access-token";

export function getCachedAccessToken(): string | null {
  return localStorage.getItem(accessTokenKey);
}

export default function SessionProvider(props: PropsWithChildren<SessionProviderProps>) {
  function setToken(value: string | null) {
    if (value !== null)
      localStorage.setItem(accessTokenKey, value);
    else
      localStorage.removeItem(accessTokenKey);

    props.setAccessToken(value);
  }

  const authProps: SessionProviderProps = {
    accessToken: props.accessToken,
    setAccessToken: setToken
  };

  return (
    <SessionContext.Provider value={authProps}>
      {props.children}
    </SessionContext.Provider>
  )
}
