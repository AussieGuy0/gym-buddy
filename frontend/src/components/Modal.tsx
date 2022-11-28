import React, { useEffect, useRef, useState } from 'react';
import * as bootstrap from 'bootstrap';

export interface ModalFooterProps {
  children: React.ReactNode;
}

export const ModalFooter: React.FC<ModalFooterProps> = ({ children }) => {
  return <div className="modal-footer">{children}</div>;
};

export interface ModalProps {
  title: string;
  content: string;
  show: boolean;
  children: React.ReactNode;
}

export const Modal: React.FC<ModalProps> = (props) => {
  const ref = useRef<HTMLDivElement>(null);
  const [modal, setModal] = useState<bootstrap.Modal>();
  useEffect(() => {
    if (ref.current != null && modal == null) {
      const newModal = new bootstrap.Modal(ref.current);
      setModal(newModal);
      newModal.show();
    }
    return () => modal?.hide();
  }, [ref, modal]);

  useEffect(() => {
    if (!props.show) {
      modal?.hide();
    }
  }, [modal, props.show]);
  return (
    <div
      className="modal fade"
      id="exampleModal"
      tabIndex={-1}
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
      ref={ref}
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">
              {props.title}
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            />
          </div>
          <div className="modal-body">{props.children}</div>
        </div>
      </div>
    </div>
  );
};
