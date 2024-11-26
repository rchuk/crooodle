"use client"

import {
  createConfiguration,
  AuthControllerApi,
  CountryControllerApi,
  WorldRegionControllerApi,
} from "@api/index";
import {createContext, PropsWithChildren, useContext} from "react";

export type Config = {
  accessToken?: string
};

export type Services = {
  authService: AuthControllerApi,
  countryService: CountryControllerApi,
  worldRegionService: WorldRegionControllerApi,
}

const ServiceContext = createContext<Services | undefined>(undefined);

export function createServices(config: Config): Services {
  const configuration = createConfiguration({
    authMethods: {
      "Bearer": config.accessToken !== undefined
        ? {
          tokenProvider: {
            getToken: () => config.accessToken ?? ""
          }
        }
        : undefined
    }
  });

  return {
    authService: new AuthControllerApi(configuration),
    countryService: new CountryControllerApi(configuration),
    worldRegionService: new WorldRegionControllerApi(configuration),
  };
}

export function ServiceProvider({
  value,
  children
}: PropsWithChildren<{ value: Services }>) {
  return (
    <ServiceContext.Provider value={value}>
      {children}
    </ServiceContext.Provider>
  );
}

export default function useServices(): Services {
  const context = useContext(ServiceContext);
  if (context === undefined)
    throw new Error("useServices must be used within a ServiceProvider");

  return context;
}
