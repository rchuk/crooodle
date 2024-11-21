import type { Metadata } from 'next'
import { Provider } from "@/components/ui/provider"
import { Inter } from 'next/font/google'
import {ReactNode} from "react";

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Crooodle'
};

export default function RootLayout({
  children,
}: {
  children: ReactNode
}) {
  return (
    <html suppressHydrationWarning lang="uk">
      <body className={inter.className}>
        <Provider>{children}</Provider>
      </body>
    </html>
  );
}
