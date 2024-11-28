"use client"

import {Card, Heading, Input, Stack} from "@chakra-ui/react";
import {Field} from "@/components/ui/field";
import {Button} from "@/components/ui/button";
import {usePathname, useRouter} from "next/navigation";
import useServices from "@lib/hooks/service-provider";
import {useEffect, useState} from "react";
import CountryAutocomplete from "@lib/components/common/autocomplete/CountryAutocomplete";
import {toaster} from "@/components/ui/toaster";
import {getRequestError} from "@lib/utils/request-utils";

export default function HotelCreateComponent() {
    const { hotelAdminService } = useServices();
    const router = useRouter();
    const pathname = usePathname();

    const [name, setName] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [countryId, setCountryId] = useState<number | null>(null);

    let id = 1;

    useEffect(() => {
        const urlParts = pathname.split('/');
        const id = urlParts[urlParts.length - 1]; // Extract last part of the URL

    }, [pathname]);

    function submit() {
        hotelAdminService.editHotel({
            id: id,
            hotelEditRequestDto: {
                name,
                address,
                countryId: countryId ?? undefined
            }
        }).then(_ => {
            toaster.create({
                title: "Hotel successfully updated",
                type: "success"
            });
            router.push('/');
        }).catch(e => {
            toaster.create({
                title: "Couldn't update hotel",
                description: getRequestError(e),
                type: "error"
            });
        })
    }

    return (
        <Card.Root w="50%" maxW="lg">
            <Card.Header>
                <Heading>
                    Edit hotel
                </Heading>
            </Card.Header>
            <Card.Body>
                <Stack gap="4" w="full">
                    <Field label="Name" required>
                        <Input value={name} onChange={e => setName(e.target.value)} />
                    </Field>
                    <Field label="Address" required>
                        <Input value={address} onChange={e => setAddress(e.target.value)} />
                    </Field>
                    <Field label="Country" required>
                        <CountryAutocomplete initialValue={countryId} setValue={setCountryId} />
                    </Field>
                </Stack>
            </Card.Body>
            <Card.Footer justifyContent="flex-end">
                <Button variant="outline" onClick={_ => router.back()}>Cancel</Button>
                <Button variant="solid" onClick={submit}>Submit</Button>
            </Card.Footer>
        </Card.Root>
    );
}
