"use client"

import {
  createConfiguration,
  AuthControllerApi,
  CountryControllerApi,
  WorldRegionControllerApi,
} from "@api/index";
import {createContext, PropsWithChildren, useContext} from "react";
import {ConfigurationParameters} from "@api/configuration";

export type Config = ConfigurationParameters;

export type Services = {
  authService: AuthControllerApi,
  countryService: CountryControllerApi,
  worldRegionService: WorldRegionControllerApi,
}

const ServiceContext = createContext<Services | undefined>(undefined);

export function createServices(config: Config): Services {
  const configuration = createConfiguration(config);

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
