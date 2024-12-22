import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material"
import { useState } from "react"


type PopupDialogProps = {
    isOpen: boolean,
    setOpen: React.Dispatch<React.SetStateAction<boolean>>
}

export default function PopupDialog(props: PopupDialogProps) {

    return (
        <Dialog open={props.isOpen} onClose={() => props.setOpen(false)}>
            <DialogTitle>Gespeichert</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Ihre Einstellungen wurden gespeichert
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => props.setOpen(false)}>Okay</Button>
            </DialogActions>
        </Dialog>
    )
}