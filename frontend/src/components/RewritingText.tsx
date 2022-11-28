import React, { useState } from 'react';
import { getRandomElement } from '../utils/utils';
import { useInterval } from '../utils/hooks';

interface RewritingTextProps {
  template: String;
  replacements: Array<string>;
}

interface ReplacementState {
  replacement: String;
  currentIndex: number;
  toAdd: boolean;
  hold: number;
}

const REPLACE_TEXT = '$REPLACE';

export const RewritingText: React.FC<RewritingTextProps> = (props) => {
  const defaultHold = 30; //TODO: Should be a option

  let [replacementState, setReplacementState] = useState<ReplacementState>({
    replacement: getRandomReplacement(),
    currentIndex: -1,
    toAdd: true,
    hold: defaultHold,
  });
  let [text, setText] = useState(createText());

  function getRandomReplacement(): string {
    return getRandomElement(props.replacements);
  }

  function getReplacementText() {
    const { replacement, currentIndex } = replacementState;
    return replacement.substring(0, currentIndex);
  }

  function createText(): string {
    const { template } = props;
    return template.replace(REPLACE_TEXT, getReplacementText());
  }

  function isStationary(state: ReplacementState): boolean {
    return state.currentIndex === state.replacement.length;
  }

  function determineNextState(state: ReplacementState): ReplacementState {
    const { replacement, currentIndex, toAdd, hold } = state;
    if (!toAdd && currentIndex === 0) {
      return {
        replacement: getRandomReplacement(),
        currentIndex: 0,
        toAdd: true,
        hold: defaultHold,
      };
    } else if (toAdd && currentIndex === replacement.length) {
      if (hold > 0) {
        return { ...replacementState, hold: hold - 1 };
      } else {
        return { ...replacementState, toAdd: false, hold: defaultHold };
      }
    } else {
      const nextIndex = toAdd ? currentIndex + 1 : currentIndex - 1;
      return { ...replacementState, currentIndex: nextIndex };
    }
  }

  useInterval(() => {
    setText(createText());
    setReplacementState(determineNextState(replacementState));
  }, 200);
  return (
    <span>
      {text}
      <span className={isStationary(replacementState) ? 'blinking' : ''}>
        |
      </span>
    </span>
  );
};
