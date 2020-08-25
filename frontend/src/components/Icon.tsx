import React from "react"

export interface IconProps {
    name: string
}

export const Icon: React.FC<IconProps> = ({name}) => {
    return (
        <img alt={name} src={`/icons/${name}.svg`}/>
    )
}