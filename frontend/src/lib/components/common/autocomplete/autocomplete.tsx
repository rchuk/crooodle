import React, { useState } from "react";
import {
  Box,
  Input,
  Text,
  HStack,
  List,
  Button, For,
} from "@chakra-ui/react";
import {
  PopoverRoot,
  PopoverContent,
  PopoverBody,
  PopoverTrigger
} from "@/components/ui/popover";

export interface Option<T> {
  value: T;
  label: string;
}

interface AutocompleteProps<T> {
  options: Option<T>[];
  placeholder?: string;
  initialOption?: T | null,
  onSelect?: (value: Option<T>) => void;
  noOptionsMessage?: string;
}

export default function Autocomplete<T extends React.Key>({
  options,
  placeholder = "Type to search...",
  initialOption,
  onSelect,
  noOptionsMessage = "No options found."
}: AutocompleteProps<T>) {
  const [inputValue, setInputValue] = useState(
    initialOption !== undefined
      ? options.find((option) => option.value === initialOption)?.label ?? ""
      : ""
  );
  const [filteredOptions, setFilteredOptions] = useState<Option<T>[]>([]);
  const [isOpen, setIsOpen] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setInputValue(value);
    const matches = options.filter((option) =>
      option.label.toLowerCase().includes(value.toLowerCase())
    );
    setFilteredOptions(matches);
    setIsOpen(true);
  };

  const handleOptionClick = (option: Option<T>) => {
    setInputValue(option.label);
    setFilteredOptions([]);
    setIsOpen(false);
    if (onSelect) {
      onSelect(option);
    }
  };

  return (
    <PopoverRoot
      open={isOpen}
      onOpenChange={v => setIsOpen(v.open)}
      autoFocus={false}
      positioning={{ sameWidth: true, placement: "bottom" }}
    >
      <PopoverTrigger width="100%">
        <HStack gap={0} width="100%">
          <Input
            value={inputValue}
            onChange={handleInputChange}
            placeholder={placeholder}
            _focus={{ borderColor: "teal.500" }}
            borderTopRightRadius={inputValue ? "0" : undefined}
            borderBottomRightRadius={inputValue ? "0" : undefined}
          />
          {inputValue && (
            <Button
              onClick={() => setInputValue("")}
              variant="outline"
              borderTopLeftRadius="0"
              borderBottomLeftRadius="0"
            >
              <Text cursor="pointer">✖</Text>
            </Button>
          )}
        </HStack>
      </PopoverTrigger>
      <PopoverContent width="100%" boxShadow="md">
        <PopoverBody p={0}>
          {filteredOptions.length > 0 ? (
            <List.Root gap={1} width="100%">
              <For each={filteredOptions}>
                {(option) => (
                  <List.Item
                    key={option.value}
                    px={4}
                    py={2}
                    cursor="pointer"
                    _hover={{ background: "teal.100" }}
                    onClick={() => handleOptionClick(option)}
                  >
                    {option.label}
                  </List.Item>
                )}
              </For>
            </List.Root>
          ) : (
            <Box px={4} py={2} color="gray.500" width="100%">
              {noOptionsMessage}
            </Box>
          )}
        </PopoverBody>
      </PopoverContent>
    </PopoverRoot>
  );
}
