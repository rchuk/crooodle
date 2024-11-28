import HotelCreateComponent from "@lib/components/hotel/hotel-create-component";
import {Flex} from "@chakra-ui/react";
import HotelEditComponent from "@lib/components/hotel/hotel-edit-component";

export default function HotelCreatePage() {
    return (
        <Flex height="100vh" justifyContent="center" alignItems="center">
            <HotelEditComponent />
        </Flex>
    );
}
