import React, { useEffect } from "react";
import { Paginator, usePaginator } from "src/components/Paginator";

export const Test = () => {
  const [page, size, totalPages, setPage, setSize, setTotal] = usePaginator(10);

  useEffect(() => {setTotal(45)}, [])
  

  return (
    <>
      <Paginator
        sizes={[5, 10, 20, 30, 40]}
        page={page}
        size={size}
        totalPages={totalPages}
        setPage={setPage}
        setSize={setSize}
        setTotal={setTotal}
      />
    </>
  )
}