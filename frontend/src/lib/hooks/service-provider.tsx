"use client"

import {
  Configuration,
  AuthControllerApi,
  CountryControllerApi,
  WorldRegionControllerApi, HotelControllerApi, ReviewControllerApi, RoomControllerApi, HotelAdminControllerApi,
} from "@api/index";
import {createContext, PropsWithChildren, useContext} from "react";

export type Config = {
  accessToken?: string
};

export type Services = {
  authService: AuthControllerApi,
  countryService: CountryControllerApi,
  worldRegionService: WorldRegionControllerApi,
  hotelService: HotelControllerApi,
  reviewService: ReviewControllerApi,
  roomService: RoomControllerApi,

  hotelAdminService: HotelAdminControllerApi
}

const ServiceContext = createContext<Services | undefined>(undefined);

export function createServices(config: Config): Services {
  const configuration = new Configuration({
    accessToken: () => config.accessToken ?? ""
  });

  return {
    authService: new AuthControllerApi(configuration),
    countryService: new CountryControllerApi(configuration),
    worldRegionService: new WorldRegionControllerApi(configuration),
    hotelService: new HotelControllerApi(configuration),
    reviewService: new ReviewControllerApi(configuration),
    roomService: new RoomControllerApi(configuration),
    hotelAdminService: new HotelAdminControllerApi(configuration)
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
