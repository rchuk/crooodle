"use client"

import {Config, createServices, ServiceProvider} from "@lib/hooks/service-provider";
import {Provider} from "@/components/ui/provider";
import {PropsWithChildren, useMemo} from "react";

export default function Providers({ children } : PropsWithChildren<void>) {
  const config: Config = useMemo(() => { return {}; }, []);
  const services = useMemo(() => createServices(config), [config]);

  return (
    <Provider>
      <ServiceProvider value={services}>
        {children}
      </ServiceProvider>
    </Provider>
  );
}
