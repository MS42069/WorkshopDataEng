import {
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Divider,
} from "@mui/material";
import React from "react";
import ErrorDisplay from "./error-display";

interface PageLayoutProps {
  title: string;
  children?: React.ReactNode;
  actions?: React.ReactNode;
}

function PageLayout({ title, children, actions }: PageLayoutProps) {
  return (
    <Card className="full-size flex-column">
      <CardHeader
        sx={{
          backgroundColor: "var(--xsoft-blue)",
          color: "var(--xsoft-white)",
        }}
        title={title}
      />
      <CardContent className="full-height">
        <ErrorDisplay>{children}</ErrorDisplay>
      </CardContent>
      {actions ? (
        <CardActions sx={{ justifyContent: "flex-end" }}>{actions}</CardActions>
      ) : (
        <></>
      )}
    </Card>
  );
}

export default PageLayout;
