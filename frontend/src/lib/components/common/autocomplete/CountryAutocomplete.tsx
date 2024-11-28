import Autocomplete from "@lib/components/common/autocomplete/autocomplete";
import useServices from "@lib/hooks/service-provider";
import {useEffect, useState} from "react";
import {CountryResponseDto} from "@api/models";

type CountryAutocompleteProps = {
  initialValue: number | null,
  setValue: (value: number | null) => void
}

export default function CountryAutocomplete({
  initialValue,
  setValue
}: CountryAutocompleteProps) {
  const { countryService } = useServices();

  const [countries, setCountries] = useState<CountryResponseDto[]>([]);

  // TODO
  useEffect(() => {
    countryService.listCountries({ limit: "300" })
      .then(response => setCountries(response.items ?? []))
      .catch();
  }, [countryService]);

  return (
    <Autocomplete
      options={countries?.map(country => ({ value: country.id!, label: country.name! })) ?? []}
      initialOption={initialValue}
      onSelect={option => setValue(option.value)}
    />
  );
}
