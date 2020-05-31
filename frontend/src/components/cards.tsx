import React from "react"

export interface CardProps {
    title: string
}

export const Card: React.FC<CardProps> = ({title, children}) => {
    return (
        <div className="card">
            <h4 className="card-header">{title}</h4>
            <div className="card-body">
                {children}
            </div>
        </div>
    )
}
