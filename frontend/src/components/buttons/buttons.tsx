import React from "react"

export interface ButtonProps {
  label: string
  additionalClass?: string

  onClick(): void
}

export const PrimaryButton: React.FC<ButtonProps> = (props) => {
  const className = createClassName("btn-primary", props.additionalClass)
  return (
    <Button
      label={props.label}
      additionalClass={className}
      onClick={props.onClick}
    />
  )
}

export const InfoButton: React.FC<ButtonProps> = (props) => {
  const className = createClassName("btn-info", props.additionalClass)
  return (
    <Button
      label={props.label}
      additionalClass={className}
      onClick={props.onClick}
    />
  )
}

export const Button: React.FC<ButtonProps> = (props) => {
  const className = createClassName("btn", props.additionalClass)
  return (
    <button className={className} onClick={props.onClick}>
      {props.label}
    </button>
  )
}

function createClassName(base: string, additional?: string) {
  return `${base} ${additional || ""}`
}
