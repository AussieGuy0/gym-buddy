import React from 'react'

export interface IconProps {
  children: React.ReactNode
}

export const Icon: React.FC<IconProps> = (props) => {
  const children = props.children
  return <>{children}</>
}
