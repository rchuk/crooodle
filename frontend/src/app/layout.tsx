import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import {PropsWithChildren} from "react";
import Providers from "@lib/providers";

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Crooodle'
};

export default function RootLayout({ children }: PropsWithChildren<void>) {
  return (
    <html suppressHydrationWarning lang="uk">
      <body className={inter.className}>
        <Providers>
          {children}
        </Providers>
      </body>
    </html>
  );
}
