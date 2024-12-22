import { el } from "date-fns/locale";
import {
  axiosInstance,
  resourceAxiosInstance,
} from "../../config/axios.config";
import {
  EmployeeAPI,
  EmployeeAPIRequest,
  EmployeeCheckConstraint,
  EmployeeCheckConstraintGET,
  EmployeeCheckConstraintPOST,
} from "../../models/employee";
import { createTimetablePath } from "../path.util";

export const fetchEmployees = () =>
  resourceAxiosInstance.get<EmployeeAPI[]>("/employees", {
    id: createTimetablePath("/employees"),
  });

export const fetchEmployeesCheckWorktime = async () => {
  const response = await axiosInstance.get<EmployeeCheckConstraint[]>(
    "/employee-worktime-constraints"
  );

  return response.data;
};

export const updateEmployeesCheckWorktime = async (
  constraint: EmployeeCheckConstraintPOST
) => {
  console.log(constraint);

  await axiosInstance.post(`/employee-worktime-constraints/${constraint.id}`, {
    accept: constraint.accepted,
    reason: constraint.reason,
  });
};

export const updateEmployee = (employee: Partial<EmployeeAPIRequest>) =>
  resourceAxiosInstance.put<EmployeeAPIRequest>(
    `/employees/${employee.id}`,
    employee,
    { cache: { update: { [createTimetablePath("/employees")]: "delete" } } }
  );

export const deleteEmployee = (employeeId: string | undefined) =>
  resourceAxiosInstance.delete(`/employees/${employeeId}`, {
    cache: { update: { [createTimetablePath("/employees")]: "delete" } },
  });

export const fetchEmployee = (employeeId: string) =>
  resourceAxiosInstance.get<EmployeeAPI>(`/employees/${employeeId}`, {
    cache: false,
  });

export const createEmployee = (employee: Partial<EmployeeAPIRequest>) =>
  resourceAxiosInstance.post<EmployeeAPIRequest>("/employees", employee, {
    cache: { update: { [createTimetablePath("/employees")]: "delete" } },
  });
