"use client"

import {Config, createServices, ServiceProvider} from "@lib/hooks/service-provider";
import {Provider} from "@/components/ui/provider";
import {PropsWithChildren, useEffect, useMemo, useState} from "react";
import {SessionProvider, getCachedAccessToken} from "@lib/hooks/session-provider";
import {Toaster} from "@/components/ui/toaster";

export default function Providers({ children } : PropsWithChildren<{}>) {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const config: Config = useMemo(
    () => { return { accessToken: accessToken ?? undefined }; },
    [accessToken]
  );
  const services = useMemo(() => createServices(config), [config]);

  useEffect(() => {
    const token = accessToken ?? getCachedAccessToken();
    setAccessToken(token);
  }, [accessToken]);

  return (
    <Provider>
      <SessionProvider accessToken={accessToken} setAccessToken={setAccessToken}>
        <ServiceProvider value={services}>
          <Toaster />
          {children}
        </ServiceProvider>
      </SessionProvider>
    </Provider>
  );
}
