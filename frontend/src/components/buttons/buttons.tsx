import React from "react"

export interface ButtonProps {
  label: string;
  additionalClass?: string;
  onClick(): void;
}

export const PrimaryButton: React.FC<ButtonProps> = (props) => {
  return createButton('btn-primary', props);
};

export const SecondaryButton: React.FC<ButtonProps> = (props) => {
  return createButton('btn-secondary', props);
};

export const InfoButton: React.FC<ButtonProps> = (props) => {
  return createButton('btn-info', props);
};

function createButton(baseClass: string, props: ButtonProps): JSX.Element {
  const className = createClassName(baseClass, props.additionalClass);
  return (
    <Button
      label={props.label}
      additionalClass={className}
      onClick={props.onClick}
    />
  );
}

export const Button: React.FC<ButtonProps> = (props) => {
  const className = createClassName('btn', props.additionalClass);
  return (
    <button className={className} onClick={props.onClick} type="button">
      {props.label}
    </button>
  );
};

function createClassName(base: string, additional?: string) {
  return `${base} ${additional || ''}`;
}
