import type { Metadata } from 'next'
import { Provider } from "@/components/ui/provider"
import { Inter } from 'next/font/google'
import {ReactNode} from "react";
import {createServices, ServiceProvider, Config} from "@lib/hooks/ServiceProvider";

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Crooodle'
};

export default function RootLayout({
  children,
}: {
  children: ReactNode
}) {
  const config: Config = {};

  return (
    <html suppressHydrationWarning lang="uk">
      <body className={inter.className}>
        <Provider>
          <ServiceProvider value={createServices(config)}>
            {children}
          </ServiceProvider>
        </Provider>
      </body>
    </html>
  );
}
